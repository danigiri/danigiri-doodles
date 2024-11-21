import { assert } from 'chai';

import {Numbers} from '../numbers';

console.log('Start tests...');

assert.equal(1, Numbers.sumBetween(1,0));
assert.equal(3, Numbers.sumBetween(1,2));
assert.equal(2, Numbers.sumBetween(-1,2));

assert.equal("1.00", Numbers.seriesSumRecursive(1));
assert.equal("1.25", Numbers.seriesSumRecursive(2));
assert.equal("1.00", Numbers.seriesSum(1));
assert.equal("1.25", Numbers.seriesSum(2));

console.log('Tests finished');
