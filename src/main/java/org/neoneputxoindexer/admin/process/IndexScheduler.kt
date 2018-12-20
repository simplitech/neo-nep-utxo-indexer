package org.neoneputxoindexer.admin.process

import br.com.simpli.model.PortugueseLanguage
import br.com.simpli.sql.TransactionPipe
import org.quartz.*

import java.util.logging.Level
import java.util.logging.Logger

import org.quartz.CronScheduleBuilder.cronSchedule

/**
 *
 * @author gil
 */
class IndexScheduler {
    companion object {

        init {
            val schedFact = org.quartz.impl.StdSchedulerFactory()
            val sched: Scheduler
            try {
                sched = schedFact.scheduler
                sched.start()
                val jobBuilder = JobBuilder.newJob(IndexJob::class.java)
                val jobDetail = jobBuilder.withIdentity("indexJob").build()
                val trigger = TriggerBuilder.newTrigger()
                        .withIdentity("triggerIndexJob")
                        .startNow()
                        .withSchedule(cronSchedule("0 15,45 * * * ?").withMisfireHandlingInstructionFireAndProceed())
                        .build()
                sched.scheduleJob(jobDetail, trigger)
            } catch (ex: Exception) {
                Logger.getLogger(IndexScheduler::class.java.name).log(Level.SEVERE, ex.message, ex)
            }

        }
    }
}

class IndexJob : Job {
    var pipe = TransactionPipe("jdbc/neoIndexerDS")

    override fun execute(context: JobExecutionContext?) {
        pipe.handle { con ->
            try {

                IndexerProcess(con, PortugueseLanguage())
                        .schedulerRun()

            } catch (ex: Exception) {
                Logger.getLogger(IndexJob::class.java.name).log(Level.SEVERE, ex.message, ex)
            }

            null
        }
    }

}