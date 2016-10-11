package assignment2;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.HashMap;
import java.math.BigInteger;

public class Main {
	
	PrintStream out;
	
	
	HashMap<Identifier, SetInterface<BigInteger>> hashmap = new HashMap<Identifier, SetInterface<BigInteger>>();
	
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
				out.print("\n" + e);
			}
		}
		in.close();
	}
	
	void processStatement(Scanner statement) throws APException {
		statement.useDelimiter("");
		statement.skip("\\s*");
		if (!statement.hasNext()) {
			throw new APException("Encountered empty line");
		} else if (statement.hasNext("\\/")) {
			return;
		} else if (statement.hasNext("[a-zA-Z]")) {
			processAssignment(statement);
		} else if (statement.hasNext("\\?")){
			statement.next();
			out.print("\n");
			processPrintStatement(statement).toString();
		} else {
			throw new APException("Invalid line: " + statement.nextLine());
		}
	}

	void processAssignment(Scanner statement) throws APException {
		Identifier identifier = getIdentifier(statement);
		
		while (!statement.hasNext("=")) {
			if (!statement.hasNext()) {
				throw new APException("= expected, not found");
			} else {
				statement.next();
			}
		}
		
		SetInterface<BigInteger> collection = processExpression(statement);
		hashmap.put(identifier, collection);
		return;
	}

	SetInterface<BigInteger> processPrintStatement (Scanner statement) throws APException {
		SetInterface<BigInteger> set = new Set<>();
		set = processExpression(statement);
		return set;
	}

	SetInterface<BigInteger> processExpression(Scanner expression) throws APException {
		SetInterface<BigInteger> firstCollection = new Set<>();
		SetInterface<BigInteger> secondCollection = new Set<>();
		
		firstCollection = processTerm(expression);
		String additiveOperator;
		
		while (expression.hasNext() && hasAdditiveOperator(expression) && !expression.hasNext("\\)")) {
			additiveOperator = expression.next();
			secondCollection = processTerm(expression);
			if (additiveOperator.equals("+")) {
				firstCollection = firstCollection.union(secondCollection);
			} else if (additiveOperator.equals("-")) {
				firstCollection = firstCollection.complement(secondCollection);
			} else {
				firstCollection = firstCollection.symmetricDifference(secondCollection);
			}
		}
		return firstCollection;
	}
	
	SetInterface<BigInteger> processTerm(Scanner expression) throws APException {
		
		SetInterface<BigInteger> firstCollection = new Set<>();
		SetInterface<BigInteger> secondCollection = new Set<>();
		
		firstCollection = processFactor(expression);
		while (expression.hasNext() && hasMultiplicativeOperator(expression)) {
			if (expression.hasNext("\\)")) {
				expression.next();
				return firstCollection;
			}
			secondCollection = processFactor(expression);
			firstCollection = firstCollection.intersection(secondCollection);
		}
		return firstCollection;
	}
	
	SetInterface<BigInteger> processFactor(Scanner expression) throws APException {
		SetInterface<BigInteger> collection = new Set<>();
		System.out.println(expression.toString());
		if (expression.hasNext("\\{")) {
			collection = processSet(expression);
		} else if (expression.hasNext("[a-zA-Z]")) {
			collection = hashmap.get(getIdentifier(expression));
		} else if (expression.hasNext("\\(")) {
			collection = processComplexFactor(expression);
			if (!expression.hasNext("\\)")) {
				throw new APException("Expected closing bracket, not found.");
			}
			expression.next();
		} else {
			throw new APException("No valid factor found, found character" + expression.next());
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
		
		while (!(set.hasNext("}"))) {
			String numberString = "";
			set.skip("\\s*");
			while (set.hasNextInt()){
				
				numberString += set.nextInt();
			}
			collection.add(new BigInteger(numberString));
			set.skip("\\s*");
			if (set.hasNext(",")) {
				set.next();
			}
		}
		set.next();
		return collection;
	}

	boolean hasAdditiveOperator(Scanner expression) {
		return (expression.hasNext("[\\|\\+\\-]"));
	}
	
	boolean hasMultiplicativeOperator(Scanner expression) {
		return (expression.hasNext("\\*"));
	}
	
	void checkEOL(Scanner expression) throws APException {
		if (expression.hasNext()) {
			throw new APException("End of line expected, but encountered more characters.");
		}
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}