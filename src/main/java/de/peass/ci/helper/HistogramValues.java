package de.peass.ci.helper;

import java.util.Arrays;
import java.util.List;

public class HistogramValues {
   private final double[] valuesCurrent;
   private final double[] valuesBefore;
   
   public HistogramValues(final List<Double> valuesCurrent, final List<Double> valuesBefore) {
      this.valuesCurrent = valuesCurrent.stream().mapToDouble(i -> i).toArray();
      this.valuesBefore = valuesBefore.stream().mapToDouble(i -> i).toArray();
   }

   public String getValuesCurrentReadable() {
      return Arrays.toString(valuesCurrent);
   }

   public String getValuesBeforeReadable() {
      return Arrays.toString(valuesBefore);
   }
}