fn main() {
    let number = "T-H-R-E-E"; // Don't change this line
    println!("Spell a number: {}", number);

    // TODO: Fix the compiler error by changing the line below without renaming the variable.
    let number = number.replace("-", "").parse::<i32>().unwrap_or_default();
    println!("Number plus two is: {}", number + 2);
}
