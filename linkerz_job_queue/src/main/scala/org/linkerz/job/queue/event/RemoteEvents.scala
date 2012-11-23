package org.linkerz.job.queue.event

import org.linkerz.job.queue.core.Job

/**
 * The Class RemoteEvents.
 *
 * @author Nguyen Duc Dung
 * @since 11/22/12 12:10 AM
 *
 */
object RemoteEvents {

  //This event was design for patten fire and forget.
  //It's main proposal is using for reporting the monitor, so it's not necessary to waiting for answer from the monitor.
  sealed trait RemoteEvents

  //Event using for sending report to the monitor
  case class Processing(job: Job) extends RemoteEvents

  //Reporting error to the monitor
  case class ErrorReport(job: Job, msg: String) extends RemoteEvents

  //When the controller receive this event. it's gonna stop.
  // In the other hand, it will send this event to the monitor when it's gonna stop.
  case class Stop(msg: String) extends RemoteEvents

  //When the controller receive this event. it's gonna restart
  case class Restart(msg: String) extends RemoteEvents
}
