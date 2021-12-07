Peass-CI
===================

Detecting and understanding performance changes is hard: Commits (often) contain many changes, so it is hard to keep track of every effect on the performance, and optimizations of the JVM might cause code changes to have unexpected side effects. By measuring the performance with statistic rigor, we can detect and understand perfromance changes and thereby avoid regressions.

The Peass-CI-Plugin enables a continuous performance measurement for Java projects in an Jenkins server. Peass-CI currently supports the following workload types:
- JUnit tests (which are transformed into performance unit tests)
- JMH benchmarks.

Currently, JUnit tests can be measured for maven and Gradle projects and JMH benchmarks only for maven projects.

By integrating Peass-CI in your build process, you will get performance measurements of each JUnit test or JMH benchmark and hints when regressions have occured. Furthermore, Peass-CI creates a call tree of the unit test or benchmark, which pinpoints the root cause of your performance changes. Therefore, the following steps are executed:
- Regression Test Selection: The unit tests which may have changed performance based on the current commit will be selected by a combination of static and dynamic code analysis.
- Performance Measurement: The selected tests will be executed (repeating them inside a VM and starting the JVM, as often as you specify it) to identify performance changes.
- Root Cause Analysis: For every identified performance change, the measurement will be repeated with additional instrumentation of your call tree to identify the method call(s) which cause your performance change (optional).

# Usage

## Configuration
After installing Peass-CI in your Jenkins, you'll have the measurement step available in your build process. 

If you are using pipeline jobs, you may add a performance measurement step like this:

```
stage('measure performance') {
   steps {
      measure VMs: 100, iterations: 10, warmup: 10, repetitions: 1000
   }
}
```

If you are using classic freestyle jobs, the build step will be called "Measure Version Performance".

After you added this stage, each build will contain performance measurements (if a code that is called by a unit test or benchmark is changed - there will be no measurements if only documentation changes).  See the [Wiki entry for measurement process configuration](https://github.com/DaGeRe/peass/wiki/Configuration-of-Measurement-Processes) for starting points for configuring the measurement step for your project.

## Example

After successfull experiment execution, you will get three things:
- the regression test selection result (telling you which test will be measured),
- the measurement result and
- the root cause analysis result (if not disabled).

For our simple demonstration, the regression test selection result looks like this:
![Regression Test Selection Results](graphs/demo-rts.png)

We can see here, that the `innerMethod` in the class `Callee` was changed, and that this changed class as called is called by the test method `ExampleTest#test`. The trace analysis confirms this test selection. By clicking on `Configuration` you can see the configuration (which you might change for a re-run in your job) and you can see the traces with the method source when clicking on the test case.

Since `ExampleTest#test` was selected, we can see the following measurement afterwards
![Measurement Results](graphs/demo-measurement.png)
In the *Changes* section, we can see that test method `test` was selected as a performance change. In the *Measurements* section, we see that the histogram clearly show the performance difference and that the measured values also clearly indicate the performance change. By clicking on *Inspect Measurement* we could see more measurent details.

In the regression test selection, we see the call tree.
![Root Cause Analysis Result](graphs/demo-rca.png)
Red indicates a performance regression in the node and green an improvement. Nodes with stripes indicate a source code change. In this example, we see that a `Thread.sleep` was increased from 1 to 20 and therefore the unit test got slower.

## Inspection

If you do not understand the measurement results, there are two main options to inspect the measurement process: The logs and the measurement dashboard. 

For regression test selection, measurement and root cause analysis, several executions of your software are necessary. If there is unexpected behaviour, this logs might be useful. To inspect the logs of the stage, click on the particular log overview. For measurements, the VM is started several times; you can have a look at all of the logs.
![Example Log Overview](graphs/demo-logs.png)

Finally, if you want to look at the performance of individual nodes or the overall measurement in more detail, click on the *Inspect Measurement* buttons of the particular node or the overall measurement of the test case. 
![Example Dashboard](graphs/demo-dashboard.png)
When looking at this, you'll the histogram of the averages of you VM runs and the evolution of the measurements inside a VM. You can select a subsect of VM runs or change the selected iterations and thereby get a better understanding of the performance measurement.


# Known Problems
- Peass only works if you use the latest version of JUnit, i.e. 4.13.x or 5.8.x, or JMH, i.e. 1.33. If you import an older version of JUnit (or it is imported by plugins you use, e.g. spring boot), please update your JUnit dependency. It is currently not possible to maintain and check the compatibility with older versions of the build tools. 

# Development

Building and updating to the latest Peass version from git is only required if you need the latest changes, e.g. if you want to change something yourself or you want to check whether a bug has been fixed. Otherwise, just use the release.

If you see an error, do not hesitate to file an issue. If you know what you are doing, you can also create a PR, but we will only merge working PRs.

## Building

Peass-CI relies on the Peass-libraries. To build them, get peass by running `git clone https://github.com/DaGeRe/peass.git && cd peass && mvn clean install -DskipTests -P buildStarter` (to build the full Peass project, and not only the basic libraries, the profile `buildStarter` needs to be built). Then, execute `mvn clean package`.

For testing, run `mvn hpi:run` and access `localhost:8080/jenkins`. 

## Development Version Running

For installing latest Peass-CI to your Jenkins installation, you may either upload it through the website (Manage Jenkins -> Manage Plugins -> Advanced -> Upload Plugin) or stop Jenkins, copy `target/peass-ci.hpi` to `~/.jenkins/plugins` (or wherever your Jenkins home is) and restart Jenkins. Afterwards, when configuring your project, the `measure`-step is available. 

# License

Peass-CI is **licensed** under the **[MIT License]** and **[AGPL License]**.

[MIT License]: https://github.com/DaGeRe/peass-ci/blob/main/LICSENSE.MIT
[AGPL License]: https://github.com/DaGeRe/peass-ci/blob/main/LICENSE.AGPL
