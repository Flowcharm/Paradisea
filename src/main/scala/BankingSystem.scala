import slick.jdbc.MySQLProfile.api._
import DatabaseConfig.db
import scala.concurrent.Await
import scala.concurrent.duration._

case class User(id: Option[Int] = None, name: String, email: String)
case class Account(id: Option[Int] = None, userId: Int, name: String, balance: BigDecimal)

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")
  def * = (id.?, name, email) <> (User.tupled, User.unapply)
}

class AccountsTable(tag: Tag) extends Table[Account](tag, "accounts") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def userId = column[Int]("user_id")
  def name = column[String]("name")
  def balance = column[BigDecimal]("balance")
  def * = (id.?, userId, name, balance) <> (Account.tupled, Account.unapply)
  def user = foreignKey("fk_user", userId, TableQuery[UsersTable])(_.id, onDelete = ForeignKeyAction.Cascade)
}

object BankingSystem {
  private val users = TableQuery[UsersTable]
  private val accounts = TableQuery[AccountsTable]

  def setup(): Unit = {
    val setupUsers = users.schema.create
    val setupAccounts = accounts.schema.create

    // Execute them separately or in sequence
    Await.result(db.run(setupUsers), 10.seconds)
    Await.result(db.run(setupAccounts), 10.seconds)
  }

  def createUser(user: User): Int = {
    val insertAction = (users returning users.map(_.id)) += user
    Await.result(db.run(insertAction), 10.seconds)
  }

  def createAccount(account: Account): Int = {
    val insertAction = (accounts returning accounts.map(_.id)) += account
    Await.result(db.run(insertAction), 10.seconds)
  }

  def readUser(id: Int): Option[User] = {
    val query = users.filter(_.id === id).result.headOption
    Await.result(db.run(query), 10.seconds)
  }

  def readAccount(id: Int): Option[Account] = {
    val query = accounts.filter(_.id === id).result.headOption
    Await.result(db.run(query), 10.seconds)
  }

  def updateUser(id: Int, updatedUser: User): Int = {
    val updateAction = users.filter(_.id === id).map(u => (u.name, u.email))
      .update((updatedUser.name, updatedUser.email))
    Await.result(db.run(updateAction), 10.seconds)
  }

  def updateAccount(id: Int, updatedAccount: Account): Int = {
    val updateAction = accounts.filter(_.id === id).map(a => (a.name, a.balance))
      .update((updatedAccount.name, updatedAccount.balance))
    Await.result(db.run(updateAction), 10.seconds)
  }

  def deleteUser(id: Int): Int = {
    val deleteAction = users.filter(_.id === id).delete
    Await.result(db.run(deleteAction), 10.seconds)
  }

  def deleteAccount(id: Int): Int = {
    val deleteAction = accounts.filter(_.id === id).delete
    Await.result(db.run(deleteAction), 10.seconds)
  }

  def listAllUsers(): Seq[User] = {
    val query = users.result
    Await.result(db.run(query), 10.seconds)
  }

  def listAllAccounts(): Seq[Account] = {
    val query = accounts.result
    Await.result(db.run(query), 10.seconds)
  }
}
