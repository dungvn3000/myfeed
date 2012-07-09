<h1>The Job Queue Framework, it is desgined for LinkerZ</h1>
<h3>08 Jul 2012</h3>
<br>
The base controller will do sync for each job. The job will be done in session.

The structure of framework.

<pre>
	Controller
    + Queue
        + Job
    + Session
    + Handler
        + HandlerInSession
        + AsyncHandler - Work with async
            + Worker - Work for this Handler
    + CallBack
        + CallBackable
</pre>


<h2>Example</h2>

<b>Sync Handler</b> 

    case class SumJob(x: Int, y: Int) extends Job {
      var result: Int = _
      def get() = {
        Some(result)
      }
    }

    class SumHandler extends Handler[SumJob] with Logging {
      def accept(job: Job) = job.isInstanceOf[SumJob]
      protected def doHandle(job: SumJob) {
        job.result = job.x + job.y
        info(job.result)
      }
    }

    @RunWith(classOf[JUnitRunner])
    class TestSimpleController extends FunSuite {
      test("testSimpleController") {
        val controller = new BaseController
        val sumJob = new SumJob(1, 2)
        val sumHandler = new SumHandler
        controller.handlers += sumHandler
        controller.add(sumJob)
        controller.start()
        Thread.sleep(2000)
        controller.stop()
        assert(sumJob.get().get == 3)
      }
    }
   


<b>Async Handler</b>

    class JobHasSubJob extends Job {
      var _parent: JobHasSubJob = _

      def get() = {
        val subJobs = new ListBuffer[JobHasSubJob]
        for (i <- 1 to 10) {
          val subJob = new JobHasSubJob {
            override def get() = {
              Thread.sleep(Random.nextInt(10000))
              //Return empty list.
              Some(new ListBuffer[JobHasSubJob])
            }
          }
          subJob._parent = this
          subJobs += subJob
        }
        Some(subJobs)
      }

      override def parent = Some(_parent)
    }

    class TestWorker(id: Int) extends Worker[JobHasSubJob, TestSession] with Logging {

      def analyze(job: JobHasSubJob, session: TestSession) = {
        info("Worker " + id + " is working...")
        val subJobs = job.get() match {
          case Some(jobs) => jobs.toList
        }
        subJobs
      }
    }

    class TestAsyncHandler extends AsyncHandler[JobHasSubJob, TestSession] {
      def accept(job: Job) = job.isInstanceOf[JobHasSubJob]
      def sessionClass = classOf[TestSession]

    }

    @RunWith(classOf[JUnitRunner])
    class TestWithAsyncHandler extends FunSuite with BeforeAndAfter with Logging {

      var job: JobHasSubJob = _
      var worker: TestWorker = _
      var handler: TestAsyncHandler = _

      before {
        job = new JobHasSubJob
        worker = new TestWorker(0)
        handler = new TestAsyncHandler
      }

      test("testBaseHandler") {
        val controller = new BaseController
        //Add worker to the handler
        for (i <- 1 to 10) {
          handler.workers += new TestWorker(i)
        }
        controller.handlers += handler
        controller.add(job)
        controller.start()
        Thread.sleep(10000)
        controller.stop()
      }
    }
