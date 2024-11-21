import { assert } from 'chai';

import {Lists} from '../lists';


console.log('Start tests...');

const lists = new Lists<String>();
assert.deepEqual([], lists.substractFromList([],["a"]));
assert.deepEqual(["b"], lists.substractFromList(["b"],["a"]));
assert.deepEqual(["b", "c", "d"], lists.substractFromList(["a", "b", "c", "d"],["a"]));

assert.deepEqual([], lists.uniqueInOrder("".split('')));
assert.deepEqual(["b"], lists.uniqueInOrder("bb".split('')));
assert.deepEqual(["a", "b", "d"], lists.uniqueInOrder("abbd".split('')));

console.log('Tests finished');
