package de.dagere.peass.ci.logs.rca;

import java.util.List;
import java.util.Map;

import de.dagere.peass.ci.VisibleAction;
import de.dagere.peass.dependency.analysis.data.TestCase;

public class RCALogOverviewAction extends VisibleAction {
   
   private final Map<TestCase, List<RCALevel>> testLevelMap;
   private String version;
   private String versionOld;
   private boolean redirectSubprocessOutputToFile;

   public RCALogOverviewAction(final Map<TestCase, List<RCALevel>> testLevelMap, final String version, final String versionOld, final boolean redirectSubprocessOutputToFile) {
      this.testLevelMap = testLevelMap;
      this.version = version;
      this.versionOld = versionOld;
      this.redirectSubprocessOutputToFile = redirectSubprocessOutputToFile;
   }

   public Map<TestCase, List<RCALevel>> getTestLevelMap() {
      return testLevelMap;
   }

   public String getVersion() {
      return version;
   }

   public String getVersionOld() {
      return versionOld;
   }

   public boolean isRedirectSubprocessOutputToFile(){
      return redirectSubprocessOutputToFile;
   }

   @Override
   public String getIconFileName() {
      return "notepad.png";
   }

   @Override
   public String getDisplayName() {
      return "Root Cause Analysis Log Overview";
   }

   @Override
   public String getUrlName() {
      return "rcaLogOverview";
   }
}
