

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * A wrapper around {@link BigDecimal} which simplifies the consistent usage of the {@link MathContext}
 * and provides a simpler API for calculations.
 * 
 * <h1>Overview</h1>
 * 
 * <p>Every {@link BigFloat} instance has a reference to a {@link Context} that specifies the {@link MathContext} to be used for all calculations and values.</p>
 * 
 * <p>The API for calculations is simplified and more consistent with the typical mathematical usage.</p>
 * <ul>
 * <li>Factory methods for values:
 *   <ul>
 *   <li><code>valueOf(BigFloat)</code></li>
 *   <li><code>valueOf(BigDecimal)</code></li>
 *   <li><code>valueOf(int)</code></li>
 *   <li><code>valueOf(long)</code></li>
 *   <li><code>valueOf(double)</code></li>
 *   <li><code>valueOf(String)</code></li>
 *   <li><code>pi()</code></li>
 *   <li><code>e()</code></li>
 *   </ul>
 * </li>
 * <li>All standard operators:
 *   <ul>
 *   <li><code>add(x)</code></li>
 *   <li><code>subtract(x)</code></li>
 *   <li><code>multiply(x)</code></li>
 *   <li><code>remainder(x)</code></li>
 *   <li><code>pow(y)</code></li>
 *   <li><code>root(y)</code></li>
 *   </ul>
 * </li>
 * <li>Calculation methods are overloaded for different value types:
 *   <ul>
 *   <li><code>add(BigFloat)</code></li>
 *   <li><code>add(BigDecimal)</code></li>
 *   <li><code>add(int)</code></li>
 *   <li><code>add(long)</code></li>
 *   <li><code>add(double)</code></li>
 *   <li>...</li>
 *   </ul>
 * </li>
 * <li>Mathematical functions are written as they are traditionally are written:
 *   <ul>
 *   <li><code>abs(x)</code></li>
 *   <li><code>log(x)</code></li>
 *   <li><code>sin(x)</code></li>
 *   <li><code>min(x1, x2, ...)</code></li>
 *   <li><code>max(x1, x2, ...)</code></li>
 *   <li>...</li>
 *   </ul>
 * </li>
 * <li>Support for advanced mathematical functions:
 *   <ul>
 *   <li><code>sqrt(x)</code></li>
 *   <li><code>log(x)</code></li>
 *   <li><code>exp(x)</code></li>
 *   <li><code>sin(x)</code></li>
 *   <li><code>cos(x)</code></li>
 *   <li><code>tan(x)</code></li>
 *   <li>...</li>
 *   </ul>
 * </li>
 * <li>Methods to access parts of a value:
 *   <ul>
 *   <li><code>getMantissa()</code></li>
 *   <li><code>getExponent()</code></li>
 *   <li><code>getIntegralPart()</code></li>
 *   <li><code>getFractionalPart()</code></li>
 *   </ul>
 * </li>
 * <li>Equals and Hashcode methods:
 *   <ul>
 *   <li><code>equals(Object)</code> that returns whether two <code>BigFloat</code> values are mathematically the same</li>
 *   <li><code>hashCode()</code> consistent with <code>equals(Object)</code></li>
 *   </ul>
 * </li>
 * <li>Comparison methods:
 *   <ul>
 *   <li><code>isEqual(BigFloat)</code></li>
 *   <li><code>isLessThan(BigFloat)</code></li>
 *   <li><code>isLessThanOrEqual(BigFloat)</code></li>
 *   <li><code>isGreaterThan(BigFloat)</code></li>
 *   <li><code>isGreaterThanOrEqual(BigFloat)</code></li>
 *   </ul>
 * </li>
 * </ul>
 * 
 * <h1>Usage</h1>
 * 
 * <p>Before doing any calculations you need to create a <code>Context</code> specifying the precision used for all calculations.</p>
 * <pre>
Context context = BigFloat.context(100); // precision of 100 digits
Context anotherContext = BigFloat.context(new MathContext(10, RoundingMode.HALF_UP); // precision of 10 digits, rounding half up
</pre>
 * 
 * <p>The <code>Context</code> can then be used to create the first value of the calculation:</p>
 * <pre>
BigFloat value1 = context.valueOf(640320);
</pre>
 * 
 * <p>The <code>BigFloat</code> instance holds a reference to the <code>Context</code>. This context is then passed from calculation to calculation.</p>
 * <pre>
BigFloat value2 = context.valueOf(640320).pow(3).divide(24);
BigFloat value3 = BigFloat.sin(value2);
</pre>
 *  
 * <p>The <code>BigFloat</code> result can be converted to other numerical types:</p>
 * <pre>
BigDecimal bigDecimalValue = value3.toBigDecimal();
double doubleValue = value3.toDouble();
long longValue = value3.toLong();
int intValue = value3.toInt();
</pre>
 */
public class BigFloat implements Comparable<BigFloat> {

	/**
	 * Manages the {@link MathContext} and provides factory methods for {@link BigFloat} values.
	 */
	public static class Context {
		private final MathContext mathContext;
		
		private Context(MathContext mathContext) {
			this.mathContext = mathContext;
		}
		
		/**
		 * Returns the {@link MathContext} of this context.
		 * 
		 * @return the {@link MathContext}
		 */
		public MathContext getMathContext() {
			return mathContext;
		}
		
		/**
		 * Returns the precision of this context.
		 * 
		 * This is equivalent to calling <code>getMathContext().getPrecision()</code>.
		 * 
		 * @return the precision
		 */
		public int getPrecision() {
			return mathContext.getPrecision();
		}

		/**
		 * Returns the {@link RoundingMode} of this context.
		 * 
		 * This is equivalent to calling <code>getMathContext().getRoundingMode()</code>.
		 * 
		 * @return the {@link RoundingMode}
		 */
		public RoundingMode getRoundingMode() {
			return mathContext.getRoundingMode();
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source {@link BigFloat} value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 */
		public BigFloat valueOf(BigFloat value) {
			return new BigFloat(value.value.round(mathContext), this); 
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source {@link BigDecimal} value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 */
		public BigFloat valueOf(BigDecimal value) {
			return new BigFloat(value.round(mathContext), this);
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source int value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 */
		public BigFloat valueOf(int value) {
			return new BigFloat(new BigDecimal(value, mathContext), this);
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source long value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 */
		public BigFloat valueOf(long value) {
			return new BigFloat(new BigDecimal(value, mathContext), this);
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source double value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 */
		public BigFloat valueOf(double value) {
			return new BigFloat(new BigDecimal(String.valueOf(value), mathContext), this); 
		}

		/**
		 * Creates a {@link BigFloat} value with this context.
		 * 
		 * @param value the source String value
		 * @return the {@link BigFloat} value with this context (rounded to the precision of this context)
		 * @throws NumberFormatException if the value is not a valid number.
		 */
		public BigFloat valueOf(String value) {
			return new BigFloat(new BigDecimal(value, mathContext), this); 
		}

		/**
		 * Returns the constant pi with this context.
		 * 
		 * @return pi with this context (rounded to the precision of this context)
		 * @see BigDecimalMath#pi(MathContext)
		 */
		public BigFloat pi() {
			return valueOf(BigDecimalMath.pi(mathContext)); 
		}

		/**
		 * Returns the constant e with this context.
		 * 
		 * @return e with this context (rounded to the precision of this context)
		 * @see BigDecimalMath#e(MathContext)
		 */
		public BigFloat e() {
			return valueOf(BigDecimalMath.e(mathContext)); 
		}

		/**
		 * Returns the factorial of n with this context.
		 * 
		 * @param n the value to calculate
		 * @return the factorial of n with this context (rounded to the precision of this context)
		 * @see BigDecimalMath#factorial(int)
		 */
		public BigFloat factorial(int n) {
			return valueOf(BigDecimalMath.factorial(n));
		}
		
		@Override
		public int hashCode() {
			return mathContext.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Context other = (Context) obj;
			return mathContext.equals(other.mathContext);
		}

		@Override
		public String toString() {
			return mathContext.toString();
		}
	}
	
	private final BigDecimal value;
	private final Context context;

	private BigFloat(BigDecimal value, Context context) {
		this.value = value;
		this.context = context;
	}
	
	/**
	 * Returns the {@link BigFloat} that is <code>this + x</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the value to add
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#add(BigDecimal, MathContext)
	 */
	public BigFloat add(BigFloat x) {
		Context c = max(context, x.context);
		return c.valueOf(value.add(x.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this + x</code>.
	 * 
	 * @param x the value to add
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#add(BigDecimal, MathContext)
	 */
	public BigFloat add(BigDecimal x) {
		return add(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this + x</code>.
	 * 
	 * @param x the value to add
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#add(BigDecimal, MathContext)
	 */
	public BigFloat add(int x) {
		return add(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this + x</code>.
	 * 
	 * @param x the value to add
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#add(BigDecimal, MathContext)
	 */
	public BigFloat add(long x) {
		return add(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this + x</code>.
	 * 
	 * @param x the value to add
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#add(BigDecimal, MathContext)
	 */
	public BigFloat add(double x) {
		return add(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this - x</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the value to subtract
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	public BigFloat subtract(BigFloat x) {
		Context c = max(context, x.context);
		return c.valueOf(value.subtract(x.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this - x</code>.
	 * 
	 * @param x the value to subtract
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	public BigFloat subtract(BigDecimal x) {
		return subtract(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this - x</code>.
	 * 
	 * @param x the value to subtract
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	public BigFloat subtract(int x) {
		return subtract(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this - x</code>.
	 * 
	 * @param x the value to subtract
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	public BigFloat subtract(long x) {
		return subtract(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this - x</code>.
	 * 
	 * @param x the value to subtract
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	public BigFloat subtract(double x) {
		return subtract(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this * x</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the value to multiply
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	public BigFloat multiply(BigFloat x) {
		Context c = max(context, x.context);
		return c.valueOf(value.multiply(x.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this * x</code>.
	 * 
	 * @param x the value to multiply
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	public BigFloat multiply(BigDecimal x) {
		return multiply(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this * x</code>.
	 * 
	 * @param x the value to multiply
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	public BigFloat multiply(int x) {
		return multiply(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this * x</code>.
	 * 
	 * @param x the value to multiply
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	public BigFloat multiply(long x) {
		return multiply(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this * x</code>.
	 * 
	 * @param x the value to multiply
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	public BigFloat multiply(double x) {
		return multiply(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this / x</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	public BigFloat divide(BigFloat x) {
		Context c = max(context, x.context);
		return c.valueOf(value.divide(x.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this / x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	public BigFloat divide(BigDecimal x) {
		return divide(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this / x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	public BigFloat divide(int x) {
		return divide(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this / x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	public BigFloat divide(long x) {
		return divide(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this / x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	public BigFloat divide(double x) {
		return divide(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is the remainder when dividing <code>this</code> by <code>x</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	public BigFloat remainder(BigFloat x) {
		Context c = max(context, x.context);
		return c.valueOf(value.remainder(x.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is the remainder when dividing <code>this</code> by <code>x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	public BigFloat remainder(BigDecimal x) {
		return remainder(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is the remainder when dividing <code>this</code> by <code>x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	public BigFloat remainder(int x) {
		return remainder(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is the remainder when dividing <code>this</code> by <code>x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	public BigFloat remainder(long x) {
		return remainder(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is the remainder when dividing <code>this</code> by <code>x</code>.
	 * 
	 * @param x the value to divide with
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	public BigFloat remainder(double x) {
		return remainder(context.valueOf(x));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this</code> to the power of <code>y</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param y the value of the power
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat pow(BigFloat y) {
		Context c = max(context, y.context);
		return c.valueOf(BigDecimalMath.pow(this.value, y.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this</code> to the power of <code>y</code>.
	 * 
	 * @param y the value of the power
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat pow(BigDecimal y) {
		return pow(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this</code> to the power of <code>y</code>.
	 * 
	 * @param y the value of the power
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat pow(int y) {
		return pow(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this</code> to the power of <code>y</code>.
	 * 
	 * @param y the value of the power
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat pow(long y) {
		return pow(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>this</code> to the power of <code>y</code>.
	 * 
	 * @param y the value of the power
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat pow(double y) {
		return pow(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>y</code>th root of <code>this</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param y the value of the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#root(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat root(BigFloat y) {
		Context c = max(context, y.context);
		return c.valueOf(BigDecimalMath.root(this.value, y.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>y</code>th root of <code>this</code>.
	 * 
	 * @param y the value of the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#root(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat root(BigDecimal y) {
		return root(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>y</code>th root of <code>this</code>.
	 * 
	 * @param y the value of the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#root(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat root(int y) {
		return root(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>y</code>th root of <code>this</code>.
	 * 
	 * @param y the value of the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#root(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat root(long y) {
		return root(context.valueOf(y));
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>y</code>th root of <code>this</code>.
	 * 
	 * @param y the value of the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#root(BigDecimal, BigDecimal, MathContext)
	 */
	public BigFloat root(double y) {
		return root(context.valueOf(y));
	}

	@Override
	public int hashCode() {
		return value.stripTrailingZeros().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BigFloat other = (BigFloat) obj;
		
		return value.compareTo(other.value) == 0;
		//return Objects.equals(value, other.value) && Objects.equals(context, other.context); 
	}

    /**
     * Returns the signum function of this {@link BigFloat}.
     *
     * @return -1, 0, or 1 as the value of this {@link BigDecimal} is negative, zero, or positive.
     */
	public int signum() {
		return value.signum();
	}

	/**
	 * Returns whether this {@link BigFloat} is negative.
	 * 
	 * @return <code>true</code> if negative, <code>false</code> if 0 or positive
	 */
	public boolean isNegative() {
		return value.signum() < 0;
	}

	/**
	 * Returns whether this {@link BigFloat} is 0.
	 * 
	 * @return <code>true</code> if 0, <code>false</code> if negative or positive
	 */
	public boolean isZero() {
		return value.signum() == 0;
	}

	/**
	 * Returns whether this {@link BigFloat} is positive.
	 * 
	 * @return <code>true</code> if positive, <code>false</code> if 0 or negative
	 */
	public boolean isPositive() {
		return value.signum() > 0;
	}
	
	@Override
	public int compareTo(BigFloat other) {
		return value.compareTo(other.value);
	}
	
	/**
	 * Returns whether <code>this</code> value is mathematically equal to the <code>other</code> value.
	 * 
	 * @param other the other {@link BigFloat} to compare with
	 * @return <code>true</code> if both values are mathematically equal (equivalent to <code>this.compareTo(other) == 0</code>
	 * @see #compareTo(BigFloat)
	 */
	public boolean isEqual(BigFloat other) {
		return compareTo(other) == 0;
	}

	/**
	 * Returns whether <code>this</code> value is mathematically less than to the <code>other</code> value.
	 * 
	 * @param other the other {@link BigFloat} to compare with
	 * @return <code>true</code> <code>this</code> value is mathematically less than to the <code>other</code> value (equivalent to <code>this.compareTo(other) &lt; 0</code>
	 * @see #compareTo(BigFloat)
	 */
	public boolean isLessThan(BigFloat other) {
		return compareTo(other) < 0;
	}

	/**
	 * Returns whether <code>this</code> value is mathematically greater than to the <code>other</code> value.
	 * 
	 * @param other the other {@link BigFloat} to compare with
	 * @return <code>true</code> <code>this</code> value is mathematically greater than to the <code>other</code> value (equivalent to <code>this.compareTo(other) &gt; 0</code>
	 * @see #compareTo(BigFloat)
	 */
	public boolean isGreaterThan(BigFloat other) {
		return compareTo(other) > 0;
	}

	/**
	 * Returns whether <code>this</code> value is mathematically less than or equal to the <code>other</code> value.
	 * 
	 * @param other the other {@link BigFloat} to compare with
	 * @return <code>true</code> <code>this</code> value is mathematically less than or equal to the <code>other</code> value (equivalent to <code>this.compareTo(other) &lt;= 0</code>
	 * @see #compareTo(BigFloat)
	 * @see #isLessThan(BigFloat)
	 * @see #isEqual(BigFloat)
	 */
	public boolean isLessThanOrEqual(BigFloat other) {
		return compareTo(other) <= 0;
	}

	/**
	 * Returns whether <code>this</code> value is mathematically greater than or equal to the <code>other</code> value.
	 * 
	 * @param other the other {@link BigFloat} to compare with
	 * @return <code>true</code> <code>this</code> value is mathematically greater than or equal to the <code>other</code> value (equivalent to <code>this.compareTo(other) &gt;= 0</code>
	 * @see #compareTo(BigFloat)
	 * @see #isGreaterThan(BigFloat)
	 * @see #isEqual(BigFloat)
	 */
	public boolean isGreaterThanOrEqual(BigFloat other) {
		return compareTo(other) >= 0;
	}

	
	/**
	 * Returns whether <code>this</code> value can be represented as <code>int</code>.
	 * 
	 * @return <code>true</code> if the value can be represented as <code>int</code> value
	 * @see BigDecimalMath#isIntValue(BigDecimal)
	 */
	public boolean isIntValue() {
		return BigDecimalMath.isIntValue(value);
	}

	/**
	 * Returns whether <code>this</code> specified {@link BigDecimal} value can be represented as <code>double</code>.
	 * 
	 * @return <code>true</code> if the value can be represented as <code>double</code> value 
	 * @see BigDecimalMath#isDoubleValue(BigDecimal)
	 */
	public boolean isDoubleValue() {
		return BigDecimalMath.isDoubleValue(value);
	}

	/**
	 * Returns the mantissa of <code>this</code> value written as <em>mantissa * 10<sup>exponent</sup></em>.
	 * 
	 * <p>The mantissa is defined as having exactly 1 digit before the decimal point.</p>
	 * 
	 * @return the mantissa
	 * @see #getExponent()
	 * @see BigDecimalMath#mantissa(BigDecimal)
	 */
	public BigFloat getMantissa() {
		return context.valueOf(BigDecimalMath.mantissa(value));
	}

	/**
	 * Returns the exponent of <code>this</code> value written as <em>mantissa * 10<sup>exponent</sup></em>.
	 * 
	 * <p>The mantissa is defined as having exactly 1 digit before the decimal point.</p>
	 * 
	 * @return the exponent
	 * @see #getMantissa()
	 * @see BigDecimalMath#exponent(BigDecimal)
	 */
	public BigFloat getExponent() {
		return context.valueOf(BigDecimalMath.exponent(value));
	}

	/**
	 * Returns the integral part of <code>this</code> value (left of the decimal point).
	 * 
	 * @return the integral part
	 * @see #getFractionalPart()
	 * @see BigDecimalMath#fractionalPart(BigDecimal)
	 */
	public BigFloat getIntegralPart() {
		return context.valueOf(BigDecimalMath.integralPart(value));
	}

	/**
	 * Returns the fractional part of <code>this</code> value (right of the decimal point).
	 * 
	 * @return the fractional part
	 * @see #getIntegralPart()
	 * @see BigDecimalMath#fractionalPart(BigDecimal)
	 */
	public BigFloat getFractionalPart() {
		return context.valueOf(BigDecimalMath.fractionalPart(value));
	}

	/**
	 * Returns the {@link Context} of <code>this</code> value.
	 *  
	 * @return the {@link Context}
	 */
	public Context getContext() {
		return context;
	}
	
	/**
	 * Returns <code>this</code> value as a {@link BigDecimal} value.
	 * 
	 * @return the {@link BigDecimal} value
	 */
	public BigDecimal toBigDecimal() {
		return value;
	}

	/**
	 * Returns <code>this</code> value as a <code>double</code> value.
	 * 
	 * @return the <code>double</code> value
	 * @see BigDecimal#doubleValue()
	 */
	public double toDouble() {
		return value.doubleValue();
	}

	/**
	 * Returns <code>this</code> value as a <code>long</code> value.
	 * 
	 * @return the <code>long</code> value
	 * @see BigDecimal#longValue()
	 */
	public long toLong() {
		return value.longValue();
	}

	/**
	 * Returns <code>this</code> value as a <code>int</code> value.
	 * 
	 * @return the <code>int</code> value
	 * @see BigDecimal#intValue()
	 */
	public int toInt() {
		return value.intValue();
	}

	@Override
	public String toString() {
		return value.toString();
	}
	
	/**
	 * Creates a {@link Context} with the specified precision and {@link RoundingMode#HALF_UP} rounding.
	 * 
	 * @param precision the precision
	 * @return the {@link Context}
	 */
	public static Context context(int precision) {
		return new Context(new MathContext(precision));
	}

	/**
	 * Creates a {@link Context} with the specified {@link MathContext}.
	 * 
	 * @param mathContext the {@link MathContext}
	 * @return the {@link Context}
	 */
	public static Context context(MathContext mathContext) {
		return new Context(mathContext);
	}

	/**
	 * Returns the {@link BigFloat} that is <code>- this</code>.
	 * 
	 * @param x the value to negate
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#negate(MathContext)
	 */
	public static BigFloat negate(BigFloat x) {
		return x.context.valueOf(x.value.negate());
	}

	/**
	 * Returns the {@link BigFloat} that is the <code>abs(this)</code> (absolute value).
	 * 
	 * @param x the value to make absolute
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimal#abs(MathContext)
	 */
	public static BigFloat abs(BigFloat x) {
		return x.context.valueOf(x.value.abs());
	}

	/**
	 * Returns the the maximum of two {@link BigFloat} values.
	 * 
	 * @param value1 the first {@link BigFloat} value to compare
	 * @param value2 the second {@link BigFloat} value to compare
	 * @return the maximum {@link BigFloat} value
	 */
	public static BigFloat max(BigFloat value1, BigFloat value2) {
		return value1.compareTo(value2) >= 0 ? value1 : value2; 
	}

	/**
	 * Returns the the maximum of n {@link BigFloat} values.
	 * 
	 * @param value1 the first {@link BigFloat} value to compare
	 * @param values the other {@link BigFloat}s value to compare
	 * @return the maximum {@link BigFloat} value
	 */
	public static BigFloat max(BigFloat value1, BigFloat... values) {
		BigFloat result = value1;
		
		for (BigFloat other : values) {
			result = max(result, other);
		}
		
		return result;
	}

	/**
	 * Returns the the minimum of two {@link BigFloat} values.
	 * 
	 * @param value1 the first {@link BigFloat} value to compare
	 * @param value2 the second {@link BigFloat} value to compare
	 * @return the minimum {@link BigFloat} value
	 */
	public static BigFloat min(BigFloat value1, BigFloat value2) {
		return value1.compareTo(value2) < 0 ? value1 : value2; 
	}

	/**
	 * Returns the the minimum of n {@link BigFloat} values.
	 * 
	 * @param value1 the first {@link BigFloat} value to compare
	 * @param values the other {@link BigFloat}s value to compare
	 * @return the minimum {@link BigFloat} value
	 */
	public static BigFloat min(BigFloat value1, BigFloat... values) {
		BigFloat result = value1;
		
		for (BigFloat other : values) {
			result = min(result, other);
		}
		
		return result;
	}

	/**
	 * Returns the {@link BigFloat} that is <code>log(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#log(BigDecimal, MathContext)
	 */
	public static BigFloat log(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.log(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>log2(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#log2(BigDecimal, MathContext)
	 */
	public static BigFloat log2(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.log2(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>log10(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#log10(BigDecimal, MathContext)
	 */
	public static BigFloat log10(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.log10(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>exp(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#exp(BigDecimal, MathContext)
	 */
	public static BigFloat exp(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.exp(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>sqrt(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#sqrt(BigDecimal, MathContext)
	 */
	public static BigFloat sqrt(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.sqrt(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>pow(x, y)</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the {@link BigFloat} value to take to the power
	 * @param y the {@link BigFloat} value to serve as exponent
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public static BigFloat pow(BigFloat x, BigFloat y) {
		Context c = max(x.context, y.context);
		return c.valueOf(BigDecimalMath.pow(x.value, y.value, c.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>root(x, y)</code>.
	 * 
	 * <p>If the two values do not have the same {@link Context}, the result will contain the {@link Context} with the larger precision.</p>
	 * 
	 * @param x the {@link BigFloat} value to calculate the n'th root
	 * @param y the {@link BigFloat} defining the root
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#pow(BigDecimal, BigDecimal, MathContext)
	 */
	public static BigFloat root(BigFloat x, BigFloat y) {
		Context c = max(x.context, y.context);
		return c.valueOf(BigDecimalMath.root(x.value, y.value, c.mathContext));
	}
	
	/**
	 * Returns the {@link BigFloat} that is <code>sin(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#sin(BigDecimal, MathContext)
	 */
	public static BigFloat sin(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.sin(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>cos(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#cos(BigDecimal, MathContext)
	 */
	public static BigFloat cos(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.cos(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>tan(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#tan(BigDecimal, MathContext)
	 */
	public static BigFloat tan(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.tan(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>cot(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#cot(BigDecimal, MathContext)
	 */
	public static BigFloat cot(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.cot(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>asin(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#asin(BigDecimal, MathContext)
	 */
	public static BigFloat asin(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.asin(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>acos(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#acos(BigDecimal, MathContext)
	 */
	public static BigFloat acos(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.acos(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>atan(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#atan(BigDecimal, MathContext)
	 */
	public static BigFloat atan(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.atan(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>acot(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#acot(BigDecimal, MathContext)
	 */
	public static BigFloat acot(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.acot(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>sinh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#sinh(BigDecimal, MathContext)
	 */
	public static BigFloat sinh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.sinh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>cosh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#cosh(BigDecimal, MathContext)
	 */
	public static BigFloat cosh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.cosh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>tanh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#tanh(BigDecimal, MathContext)
	 */
	public static BigFloat tanh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.tanh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>coth(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#coth(BigDecimal, MathContext)
	 */
	public static BigFloat coth(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.coth(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>asinh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#asinh(BigDecimal, MathContext)
	 */
	public static BigFloat asinh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.asinh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>acosh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#acosh(BigDecimal, MathContext)
	 */
	public static BigFloat acosh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.acosh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>atanh(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#atanh(BigDecimal, MathContext)
	 */
	public static BigFloat atanh(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.atanh(x.value, x.context.mathContext));
	}

	/**
	 * Returns the {@link BigFloat} that is <code>acoth(x)</code>.
	 * 
	 * @param x the value
	 * @return the resulting {@link BigFloat}
	 * @see BigDecimalMath#acoth(BigDecimal, MathContext)
	 */
	public static BigFloat acoth(BigFloat x) {
		return x.context.valueOf(BigDecimalMath.acoth(x.value, x.context.mathContext));
	}

	private static Context max(Context left, Context right) {
		return left.mathContext.getPrecision() > right.mathContext.getPrecision() ? left : right;
	}
}