function binomialCoeff(n, k) {
    if ((k === 0) || (k === n)) {
        return 1
    } else {
        return binomialCoeff(n - 1, k - 1) + binomialCoeff(n - 1, k)
    }
}

function isDivisible(value) {
    return (value % this == 0)
}

function isPrime(p) {
    if ((p === 0) || (p === 1)) {
        return true
    } else {
        let coeff = Array.from(Array(p + 1), (x, index) => binomialCoeff(p, index))
        coeff.shift()
        coeff.splice(-1, 1)
        return coeff.every(isDivisible, p)
    }

}


let seeder = function () {
    let bigseed = 0n;

    for(let i = 0n; i < 1024; i++)
    {
        bigseed += (Math.random()*10)*Math.exp(i);
    }

    return bigseed;
}

let p = seeder();
console.time("isPrime")
isPrime(p)
console.timeEnd("isPrime")
alert(`isPrime(${p}):`+ isPrime(p))