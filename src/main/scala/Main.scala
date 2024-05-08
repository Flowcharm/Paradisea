import org.slf4j.LoggerFactory
import scala.io.StdIn

object Main {
  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    logger.info("Application started successfully!")
    println("Welcome to the Banking System.")

    var continue = true
    while (continue) {
      println("\nMenu:")
      println("1. Create User")
      println("2. Create Account")
      println("3. Read User")
      println("4. Read Account")
      println("5. Update User")
      println("6. Update Account")
      println("7. List All Users")
      println("8. List All Accounts")
      println("9. Delete User")
      println("10. Delete Account")
      println("11. Exit")
      print("Choose an option: ")

      // Menu logic remains the same as in the previous code
      StdIn.readLine() match {
        case "1" => createUser()
        case "2" => createAccount()
        case "3" => readUser()
        case "4" => readAccount()
        case "5" => updateUser()
        case "6" => updateAccount()
        case "7" => listAllUsers()
        case "8" => listAllAccounts()
        case "9" => deleteUser()
        case "10" => deleteAccount()
        case "11" =>
          continue = false
          println("Exiting. Goodbye!")
        case _ => println("Invalid option. Please try again.")
      }
    }
  }

  // Menu methods
  private def createUser(): Unit = {
    print("Enter user name: ")
    val name = StdIn.readLine()
    print("Enter user email: ")
    val email = StdIn.readLine()
    val newUserId = BankingSystem.createUser(User(None, name, email))
    println(s"New User created with ID: $newUserId")
  }

  def createAccount(): Unit = {
    print("Enter user ID: ")
    val userId = StdIn.readInt()
    print("Enter account name: ")
    val name = StdIn.readLine()
    print("Enter initial balance: ")
    val balanceInput = StdIn.readLine()  // Read input as string
    try {
      val balance = BigDecimal(balanceInput)  // Convert string to BigDecimal
      val newAccountId = BankingSystem.createAccount(Account(None, userId, name, balance))
      println(s"New Account created with ID: $newAccountId")
    } catch {
      case e: NumberFormatException => println("Invalid balance. Please enter a valid number.")
    }
  }

  private def updateAccount(): Unit = {
    print("Enter account ID to update: ")
    val accountId = StdIn.readInt()
    print("Enter new user ID: ")
    val userId = StdIn.readInt()
    print("Enter new account name: ")
    val name = StdIn.readLine()
    print("Enter new balance: ")
    val balanceInput = StdIn.readLine()  // Read input as string
    try {
      val balance = BigDecimal(balanceInput)  // Convert string to BigDecimal
      val updatedCount = BankingSystem.updateAccount(accountId, Account(Some(accountId), userId, name, balance))
      println(s"Updated $updatedCount account(s).")
    } catch {
      case e: NumberFormatException => println("Invalid balance. Please enter a valid number.")
    }
  }


  private def readUser(): Unit = {
    print("Enter user ID to read: ")
    val userId = StdIn.readInt()
    BankingSystem.readUser(userId) match {
      case Some(user) => println(s"User: $user")
      case None => println(s"No user found with ID $userId")
    }
  }

  private def readAccount(): Unit = {
    print("Enter account ID to read: ")
    val accountId = StdIn.readInt()
    BankingSystem.readAccount(accountId) match {
      case Some(account) => println(s"Account: $account")
      case None => println(s"No account found with ID $accountId")
    }
  }

  private def updateUser(): Unit = {
    print("Enter user ID to update: ")
    val userId = StdIn.readInt()
    print("Enter new name: ")
    val name = StdIn.readLine()
    print("Enter new email: ")
    val email = StdIn.readLine()
    val updatedCount = BankingSystem.updateUser(userId, User(Some(userId), name, email))
    println(s"Updated $updatedCount user(s).")
  }

  private def listAllUsers(): Unit = {
    val allUsers = BankingSystem.listAllUsers()
    println(s"All Users: $allUsers")
  }

  private def listAllAccounts(): Unit = {
    val allAccounts = BankingSystem.listAllAccounts()
    println(s"All Accounts: $allAccounts")
  }

  private def deleteUser(): Unit = {
    print("Enter user ID to delete: ")
    val userId = StdIn.readInt()
    val deletedCount = BankingSystem.deleteUser(userId)
    println(s"Deleted $deletedCount user(s).")
  }

  private def deleteAccount(): Unit = {
    print("Enter account ID to delete: ")
    val accountId = StdIn.readInt()
    val deletedCount = BankingSystem.deleteAccount(accountId)
    println(s"Deleted $deletedCount account(s).")
  }
}
