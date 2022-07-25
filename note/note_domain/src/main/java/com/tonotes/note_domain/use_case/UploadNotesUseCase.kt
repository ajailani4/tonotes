package com.tonotes.note_domain.use_case

import androidx.work.*
import com.tonotes.core.util.Constants.WorkerName
import com.tonotes.note_data.worker.UploadNotesWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadNotesUseCase @Inject constructor(
    private val workManager: WorkManager
) {
    operator fun invoke(backupType: Int) {
        val uploadNotesRequest = when (backupType) {
            0 -> {
                OneTimeWorkRequestBuilder<UploadNotesWorker>().build()
            }

            1 -> {
                PeriodicWorkRequestBuilder<UploadNotesWorker>(1, TimeUnit.DAYS)
                    .setInitialDelay(1, TimeUnit.DAYS)
                    .build()
            }

            2 -> {
                PeriodicWorkRequestBuilder<UploadNotesWorker>(7, TimeUnit.DAYS)
                    .setInitialDelay(7, TimeUnit.DAYS)
                    .build()
            }

            else -> {
                OneTimeWorkRequestBuilder<UploadNotesWorker>().build()
            }
        }

        if (backupType == 0) {
            workManager.enqueueUniqueWork(
                WorkerName.UPLOAD_NOTES_WORKER,
                ExistingWorkPolicy.REPLACE,
                uploadNotesRequest as OneTimeWorkRequest
            )
        } else {
            workManager.enqueueUniquePeriodicWork(
                WorkerName.UPLOAD_NOTES_WORKER,
                ExistingPeriodicWorkPolicy.REPLACE,
                uploadNotesRequest as PeriodicWorkRequest
            )
        }
    }
}