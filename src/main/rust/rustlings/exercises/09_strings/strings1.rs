use std::str::FromStr;

// TODO: Fix the compiler error without changing the function signature.
fn current_favorite_color() -> String {
    FromStr::from_str("blue").expect("wrong str")
}

fn main() {
    let answer = current_favorite_color();
    println!("My current favorite color is {answer}");
}
