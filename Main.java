package assignment2;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.HashMap;
import java.math.BigInteger;

public class Main {
	
	PrintStream out;
	HashMap<Identifier, SetInterface<BigInteger>> hashmap = new HashMap<Identifier, SetInterface<BigInteger>>();
	int teller = 0;
	int tellerOpenBracket = 0;
	int tellerClosedBracket = 0;
	int checkBracket = 0;
	int check1 = 0;
	
	Main() {
		out = new PrintStream(System.out);
	}
	
	void start() {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			try {
				Scanner statement = new Scanner(in.nextLine());
				processStatement(statement);
			} catch (APException e) {
				clearInitialValues();
				out.print(e + "\n");
			}
		}
		in.close();
	}
	
	void processStatement(Scanner statement) throws APException {
		
		statement = deleteSpaces(statement);
		
		if (!statement.hasNext()) {
			throw new APException("Encountered empty line");
		} else if (statement.hasNext("\\/")) {
			return;
		} else if (statement.hasNext("[a-zA-Z]")) {
			processAssignment(statement);
		} else if (statement.hasNext("\\?")) {
			statement.next();
			SetInterface<BigInteger> setToPrint = new Set<BigInteger>();
			setToPrint = processPrintStatement(statement);
			printSet(setToPrint);
		} else {
			throw new APException("Invalid line");
		}
		
		clearInitialValues();
	}

	void processAssignment(Scanner statement) throws APException {
		
		statement = deleteSpaces(statement);
		Identifier identifier = getIdentifier(statement);
		
		while (!statement.hasNext("=")) {
			if (!statement.hasNext()) {
				throw new APException("= expected, not found");
			} else {
				statement.next();
			}
		}
		
		statement.next();
		deleteSpaces(statement);
		SetInterface<BigInteger> collection = processExpression(statement);
		
		while (statement.hasNext()) {
			if (statement.hasNext("[a-zA-Z]")) {
				throw new APException("Invalid line");
			} else {
				statement.next();
			}
		}
		
		if (hashmap.containsKey(identifier)) {
			hashmap.remove(identifier);
		}
		hashmap.put(identifier, collection);
		
		return;
	}

	SetInterface<BigInteger> processPrintStatement(Scanner statement) throws APException {
		
		statement = deleteSpaces(statement);
		SetInterface<BigInteger> setToPrint = new Set<BigInteger>();
		setToPrint = processExpression(statement);
		
		return setToPrint;
	}

	SetInterface<BigInteger> processExpression(Scanner expression) throws APException {
		
		SetInterface<BigInteger> firstCollection = new Set<BigInteger>();
		SetInterface<BigInteger> secondCollection = new Set<BigInteger>();
		firstCollection = processTerm(expression);
		String additiveOperator;
		expression = deleteSpaces(expression);
		
		while (expression.hasNext() && hasAdditiveOperator(expression) && !expression.hasNext("\\)")) {
			additiveOperator = expression.next();
			expression = deleteSpaces(expression);
			secondCollection = processTerm(expression);
			if (additiveOperator.equals("+")) {
				firstCollection = firstCollection.union(new Set<BigInteger>(secondCollection));
			} else if (additiveOperator.equals("-")) {
				firstCollection = firstCollection.complement(secondCollection);
			} else if (additiveOperator.equals("|")) {
				firstCollection = firstCollection.symmetricDifference(secondCollection);
			} else {
				expression.next();
			}
		}
		
		return firstCollection;
	}
	
	SetInterface<BigInteger> processTerm(Scanner expression) throws APException {
		
		expression = deleteSpaces(expression);
		SetInterface<BigInteger> firstCollection = new Set<BigInteger>();
		SetInterface<BigInteger> secondCollection = new Set<BigInteger>();
		firstCollection = processFactor(expression);
		if (expression.hasNext("\\)") && checkBracket == 0){
			throw new APException("An opening bracket is missing");
		}
		expression = deleteSpaces(expression);
		
		while (expression.hasNext() && hasMultiplicativeOperator(expression)) {
			expression = deleteSpaces(expression);
			if (expression.hasNext("\\)")) {
				expression.next();
				return firstCollection;
			}
			expression.next();
			secondCollection = processFactor(expression);
			firstCollection = firstCollection.intersection(secondCollection);
		}
		
		return firstCollection;
	}
	
	SetInterface<BigInteger> processFactor(Scanner expression) throws APException {
		
		SetInterface<BigInteger> collection = new Set<BigInteger>();
		expression = deleteSpaces(expression);
		if (expression.hasNext("\\{")) {
			collection = processSet(expression);
		} else if (expression.hasNext("[a-zA-Z]")) {
			collection = hashmap.get(getIdentifier(expression));
			expression = deleteSpaces(expression);
			if (collection == null) {
				throw new APException("Collection does not exist");
			}
		} else if (expression.hasNext("\\(")) {
			expression = deleteSpaces(expression);
			checkBracket = 1;
			collection = processComplexFactor(expression);
			if (!expression.hasNext("\\)")) {
				throw new APException("Expected closing bracket, not found.");
			}
			expression.next();
		} else {
			throw new APException("No valid factor found, found character");
		}
		
		return collection;
	}
	
	Identifier getIdentifier(Scanner expression) throws APException {
		
		Identifier identifier = new Identifier();
		while (expression.hasNext("[a-zA-Z0-9]")) {
			identifier.add(expression.next());
		}
		
		return identifier;
	}
	
	SetInterface<BigInteger> processComplexFactor (Scanner expression) throws APException {
		
		expression.next();
		return processExpression(expression);
	}
	
	SetInterface<BigInteger> processSet(Scanner set) throws APException {
		
		set.next();
		SetInterface<BigInteger> collection = new Set<>();
		set = deleteSpaces(set);
		if (set.hasNext(",")) {
			throw new APException ("Set needs to start with a number");
		}
		
		while (!(set.hasNext("}"))) {
			String numberString = "";
			set.skip("\\s*");
			while (set.hasNextInt()) {
				if (set.hasNext("0"))	{
					numberString += set.nextInt();
					if (set.hasNextInt() && teller==0){
						throw new APException ("after zero, no new numbers");
					}
				} else if (set.hasNextInt()) {
					numberString += set.nextInt();
					teller= teller + 1;
					if (set.hasNext("[a-zA-Z]")){
						throw new APException ("This value cannot be used");
					}
				} else if (!set.hasNext(",")){
					set.next();
				}
			}
			
			teller = 0;
			
			if (!numberString.isEmpty()) {
				collection.add(new BigInteger(numberString));
			}
			
			if (set.hasNext("-") || set.hasNext("\\+")){
				throw new APException ("No positive/negative values allowed");
			} else if (set.hasNext("\\s")){
				set.next();
				if (set.hasNextInt()){
					throw new APException ("Comma is missing");
				}
			}
			
			set.skip("\\s*");
			if (set.hasNext(",")) {
				set.next();
				set = deleteSpaces(set);
				if (set.hasNext(",")) {
					throw new APException ("Should be a number after the comma");
				} else if (set.hasNext("}")){
					throw new APException ("Should be a number after the comma");
				}
			}

			if (!set.hasNext()){
				throw new APException ("There should be a }");
			}
		}
		
		set.next();
		set = deleteSpaces(set);
		
		if (set.hasNext("[a-zA-Z]")){
			throw new APException("should be no character after a closed bracket");
		} else if (set.hasNext("\\{")){
			throw new APException("should be no open bracket after a closed bracket");
		} else if (set.hasNext("\\(")){
			throw new APException ("should be no open ( after a closed bracket");
		}
		
		return collection;
	}

	boolean hasAdditiveOperator(Scanner expression) {
		return (expression.hasNext("\\s*[\\|\\+\\-]"));
	}
	
	boolean hasMultiplicativeOperator(Scanner expression) {
		return (expression.hasNext("\\*"));
	}
	
	void clearInitialValues() {
		checkBracket = 0;
		return;
	}
	
	Scanner deleteSpaces (Scanner expression){
		
		expression.useDelimiter("");
		expression.skip("\\s*");
		
		return expression;
	}
	
	void printSet(SetInterface<BigInteger> set) {
		
		String printString = "";
		for (int i = 0; i < set.size(); i++) {
			printString += set.get(i);
			if (i < set.size() - 1) {
				printString += " ";
			}
		}
		System.out.print(printString += "\n");
		
		return;
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}