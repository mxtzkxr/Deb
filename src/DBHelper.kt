import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBHelper(
    val dbName: String,
    val host: String = "localhost",
    val port: Int = 3306,
    val username: String = "root",
    val password: String = "root"
) {
    val conn: Connection
    init{
        try {
            Class.forName("com.mysql.jdbc.Driver")
        } catch (e: ClassNotFoundException) {
            println("Unable to load class.")
            e.printStackTrace()
        }
        conn = DriverManager.getConnection(
            "jdbc:mysql://$host:$port/$dbName?serverTimezone=UTC",
            username, password
        )
    }

    fun createTables(){
        val statement = conn.createStatement()
        statement.execute("CREATE TABLE IF NOT EXISTS `a` (" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "text VARCHAR(30) NOT NULL, " +
                "PRIMARY KEY(`id`)" +
                ")")
    }

    fun insertInto(dbName: String, tableName: String, fields: String, values: String){
        try{
            conn.createStatement().apply {
                execute("USE $dbName")
                execute("INSERT INTO `$tableName` ($fields) VALUES ($values)")
            }
        }catch (ex: SQLException){
            println("Не удалось добавить запись")
        }
    }

    /*fun load(filename: String){
        val csvSplitter = ","
        var line = ""
        try{
        val br = BufferedReader(
            InputStreamReader(
            FileInputStream(filename), "UTF-8"))
            while (br.readLine().also { line = it } != null) {
                val c: List<String> = line.split(csvSplitter)
            }

        }catch (e: Exception){
            println("Ошибка при чтении из файла: ${e.message}")
        }
    }*/
}