
use std::io;
use std::cmp::Ordering;

use rand::Rng;

fn main() {
    println!("Guess the number!");    
    let num = rand::thread_rng().gen_range(1..=100);
    
    println!("The secret number is: {num}");
    
    loop {
        
        let mut guess = String::new();
        println!("Please input your guess.");
        
        io::stdin()
        .read_line(&mut guess)
        .expect("Failed to read line");
        let guess: u32 = match guess.trim().parse() {
            Ok(n) => n,
            Err(_) => continue,
        };
        
        
        match guess.cmp(&num) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("You win!");
                break;
            },
        }
    }   
    
}
