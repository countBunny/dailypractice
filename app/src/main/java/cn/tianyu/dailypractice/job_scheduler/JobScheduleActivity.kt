package cn.tianyu.dailypractice.job_scheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.tianyu.dailypractice.R

class JobScheduleActivity : AppCompatActivity() {

    private var mJobId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_schedule)

        startService(Intent(this, TestJobService::class.java))
    }

    fun scheduleOnce(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
            scheduler?.apply {
                println("------>成功获取到 JobSchduler")
                val jobInfo = JobInfo.Builder(mJobId++, ComponentName(this@JobScheduleActivity, TestJobService::class.java))
                        .let {
                            it.setMinimumLatency(5 * 1000)
                            it.setRequiresCharging(true)
                            it.setOverrideDeadline(5 * 1000)
                        }.build()
                schedule(jobInfo)
            }
        }
    }

    fun cancelAll(v: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler
            scheduler?.apply {
                println("------>成功获取到 JobSchduler 并取消所有Job")
                cancelAll()
            }
        }
    }
}
