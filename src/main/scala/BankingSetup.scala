object BankingSetup {
  def main(args: Array[String]): Unit = {
    println("Setting up the banking system database...")
    BankingSystem.setup()
    println("Database setup complete.")
  }
}
