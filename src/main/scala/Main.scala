import org.slf4j.LoggerFactory
import scala.io.StdIn

object Main {
  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    logger.info("User-oriented banking system started successfully!")
    println("Welcome to the Banking System.")

    var continue = true
    while (continue) {
      println("\nMenu:")
      println("1. Deposit Money")
      println("2. Withdraw Money")
      println("3. Transfer Money")
      println("4. Check Balance")
      println("5. Exit")
      print("Choose an option: ")

      StdIn.readLine() match {
        case "1" => depositMoney()
        case "2" => withdrawMoney()
        case "3" => transferMoney()
        case "4" => checkBalance()
        case "5" =>
          continue = false
          println("Exiting. Goodbye!")
        case _ =>
          println("Invalid option. Please try again.")
      }
    }
  }

  // Deposit money into a specific account
  def depositMoney(): Unit = {
    print("Enter your account ID: ")
    val accountId = try { StdIn.readInt() } catch { case _: Exception => -1 }
    if (accountId == -1) {
      println("Invalid account ID. Please enter a numeric value.")
      return
    }

    print("Enter amount to deposit: ")
    val amountInput = StdIn.readLine()
    try {
      val amount = BigDecimal(amountInput)
      val currentAccount = BankingSystem.readAccount(accountId)
      currentAccount match {
        case Some(account) =>
          val updatedBalance = account.balance + amount
          BankingSystem.updateAccount(accountId, account.copy(balance = updatedBalance))
          println(s"Successfully deposited $amount. New balance: $updatedBalance")
        case None =>
          println(s"No account found with ID $accountId.")
      }
    } catch {
      case _: NumberFormatException =>
        println("Invalid amount. Please enter a numeric value.")
    }
  }

  // Withdraw money from a specific account
  def withdrawMoney(): Unit = {
    print("Enter your account ID: ")
    val accountId = try { StdIn.readInt() } catch { case _: Exception => -1 }
    if (accountId == -1) {
      println("Invalid account ID. Please enter a numeric value.")
      return
    }

    print("Enter amount to withdraw: ")
    val amountInput = StdIn.readLine()
    try {
      val amount = BigDecimal(amountInput)
      val currentAccount = BankingSystem.readAccount(accountId)
      currentAccount match {
        case Some(account) =>
          if (account.balance >= amount) {
            val updatedBalance = account.balance - amount
            BankingSystem.updateAccount(accountId, account.copy(balance = updatedBalance))
            println(s"Successfully withdrew $$amount. New balance: $$updatedBalance")
          } else {
            println("Insufficient funds.")
          }
        case None =>
          println(s"No account found with ID $accountId.")
      }
    } catch {
      case _: NumberFormatException =>
        println("Invalid amount. Please enter a numeric value.")
    }
  }

  // Transfer money between two accounts
  def transferMoney(): Unit = {
    print("Enter your account ID: ")
    val senderAccountId = try { StdIn.readInt() } catch { case _: Exception => -1 }
    if (senderAccountId == -1) {
      println("Invalid account ID. Please enter a numeric value.")
      return
    }

    print("Enter recipient account ID: ")
    val recipientAccountId = try { StdIn.readInt() } catch { case _: Exception => -1 }
    if (recipientAccountId == -1) {
      println("Invalid recipient account ID. Please enter a numeric value.")
      return
    }

    print("Enter amount to transfer: ")
    val amountInput = StdIn.readLine()
    try {
      val amount = BigDecimal(amountInput)
      val senderAccountOpt = BankingSystem.readAccount(senderAccountId)
      val recipientAccountOpt = BankingSystem.readAccount(recipientAccountId)

      (senderAccountOpt, recipientAccountOpt) match {
        case (Some(senderAccount), Some(recipientAccount)) =>
          if (senderAccount.balance >= amount) {
            // Update balances
            val newSenderBalance = senderAccount.balance - amount
            val newRecipientBalance = recipientAccount.balance + amount
            BankingSystem.updateAccount(senderAccountId, senderAccount.copy(balance = newSenderBalance))
            BankingSystem.updateAccount(recipientAccountId, recipientAccount.copy(balance = newRecipientBalance))
            println(s"Successfully transferred $$amount to account $recipientAccountId.")
          } else {
            println("Insufficient funds.")
          }
        case _ =>
          println(s"Invalid account IDs provided.")
      }
    } catch {
      case _: NumberFormatException => println("Invalid amount. Please enter a numeric value.")
    }
  }

  // Check the balance of a specific account
  def checkBalance(): Unit = {
    print("Enter your account ID: ")
    val accountId = try { StdIn.readInt() } catch { case _: Exception => -1 }
    if (accountId == -1) {
      println("Invalid account ID. Please enter a numeric value.")
      return
    }

    BankingSystem.readAccount(accountId) match {
      case Some(account) =>
        println(s"Your current balance is: $$${account.balance}")
      case None =>
        println(s"No account found with ID $accountId.")
    }
  }
}
