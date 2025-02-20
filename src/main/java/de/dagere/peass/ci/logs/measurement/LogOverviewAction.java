package de.dagere.peass.ci.logs.measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.dagere.peass.ci.VisibleAction;
import de.dagere.peass.ci.logs.LogFiles;
import de.dagere.peass.dependency.analysis.data.TestCase;

public class LogOverviewAction extends VisibleAction {
   
   private Map<TestCase, List<LogFiles>> logFiles = new HashMap<>();
   private String version;
   private String versionOld;
   private boolean redirectSubprocessOutputToFile;

   public LogOverviewAction(final Map<TestCase, List<LogFiles>> logFiles, final String version, final String versionOld, final boolean redirectSubprocessOutputToFile) {
      this.logFiles = logFiles;
      this.version = version;
      this.versionOld = versionOld;
      this.redirectSubprocessOutputToFile = redirectSubprocessOutputToFile;
   }
   
   public Map<TestCase, List<LogFiles>> getLogFiles() {
      return logFiles;
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
      return "Performance Measurement Log Overview";
   }

   @Override
   public String getUrlName() {
      return "measurementLogOverview";
   }


}
