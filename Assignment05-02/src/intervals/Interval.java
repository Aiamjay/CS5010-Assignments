package intervals;

import java.util.Objects;

/**
 * This class represents a 1-dimensional interval. The interval is characterized by a start and an
 * end, both integral values.
 */
public class Interval {
  int start;
  int end;

  /**
   * Construct an interval given its start and end values.
   *
   * @param start the start
   * @param end   the end
   * @throws IllegalArgumentException if start is greater than end
   */
  public Interval(int start, int end) throws IllegalArgumentException {
    if (start > end) {
      throw new IllegalArgumentException("Invalid interval");
    }
    this.start = start;
    this.end = end;
  }

  /**
   * Compute and return an interval that represents the intersection of this and another interval.
   * The intersection is defined as the smallest interval that overlaps with both intervals. If no
   * intersection exists, return an interval with equal and very low start and end values.
   *
   * @param other the other interval
   * @return the interval that is the intersection of this and the other interval
   */
  public Interval intersect(Interval other) {
    int min = Math.max(this.start, other.start);
    int max = Math.min(this.end, other.end);
    if (min > max) {
      min = max = Integer.MIN_VALUE;
    }
    return new Interval(min, max);
  }

  /**
   * Computes and returns the union of this and another interval. The union is defined as the
   * smallest interval that both intervals overlap with.
   *
   * @param other the other interval
   * @return the union of the two intervals
   */
  public Interval union(Interval other) {
    return new Interval(Math.min(this.start, other.start), Math.max(this.end,
            other.end));
  }

  /**
   * Return a string-representation of this interval.
   *
   * @return a string of the format start,end
   */
  public String toString() {
    return "" + start + "," + end;
  }

  /**
   * Sameness test for two intervals. Two intervals are deemed to be equal to each other if their
   * start and end values coincide.
   *
   * @param other the other object to be tested for equality
   * @return true if the two intervals are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Interval)) {
      return false;
    }
    Interval otherInterval = (Interval) other;
    return this.start == otherInterval.start && this.end == otherInterval.end;
  }

  /**
   * Hashcode for the interval, using start and end.
   *
   * @return the hash value for this interval.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.start, this.end);
  }
}