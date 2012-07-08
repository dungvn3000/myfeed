<h1>The Job Queue Framework, it is desgined for LinkerZ</h1>
<h3>08 Jul 2012</h3>
<br>
The base controller will do sync for each job. The job will be done in session.

The structure of framework.

<pre>
	Controller
    + Queue
        + Job
            + SubJob
    + Handler
</pre>


