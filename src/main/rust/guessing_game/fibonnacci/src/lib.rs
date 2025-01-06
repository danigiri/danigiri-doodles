pub fn fib(n: i32) -> i32 {
    if n <= 1 {
        return n;
    }
    return fib(n - 1) + fib(n - 2);
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        assert_eq!(fib(0), 0);
        assert_eq!(fib(1), 1);
    }
}
