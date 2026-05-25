package soto.zuleyca.tareasapp

import android.content.Context
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.Volatile

@Database(
    entities = [TaskEntity::class],
    version = 1
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Tareas que se cargan la primera vez que se
        // instala la app. Edita esta lista con tus
        // tareas reales del reto con el socio formador.
        private val TAREAS_INICIALES = listOf(
            TaskEntity(
                titulo = "Connect log out functionality",
                completado = true
            ),
            TaskEntity(
                titulo = "Display username",
                completado = true
            ),
            TaskEntity(
                titulo = "Display user email",
                completado = true
            ),
            TaskEntity(
                titulo = "Create Profile & Settings screen layout",
                completado = true
            ),
            TaskEntity(
                titulo = "Change background image to \"AuthBackground\"",
                completado = true
            ),
            TaskEntity(
                titulo = "Implement profile information section",
                completado = true
            ),
            TaskEntity(
                titulo = "Implement chart theme selection UI",
                completado = true
            ),
            TaskEntity(
                titulo = "Implement toast UI component",
                completado = true
            ),
            TaskEntity(
                titulo = "Add Terms of Service and Licenses sections",
                completado = true
            ),
            TaskEntity(
                titulo = "Add logout button UI",
                completado = true
            ),
            TaskEntity(
                titulo = "Implement chart color theme selector",
                completado = true
            ),
            TaskEntity(
                titulo = "Check PRs",
                completado = false
            ),
            TaskEntity(
                titulo = "Fix background image in header",
                completado = false
            )
        // Agrega aquí las tuyas reales.
        )

        fun getInstance(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(
                this
            ){
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "tasks_db"
                    )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
// Insertamos las tareas iniciales en un
// hilo separado. NUNCA en el main thread.
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getInstance(context).taskDao()
                                TAREAS_INICIALES.forEach { tarea ->
                                    dao.insert(tarea)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}