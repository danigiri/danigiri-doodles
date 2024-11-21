
import { assert } from 'chai';

import {Strings} from '../strings';


console.log('Start tests...')
assert.equal("oof", Strings.reverseWord("foo"))
assert.equal("oof", Strings.reverseWords("foo"))

assert.equal("1234", Strings.mask("1234",4));
assert.equal("#234", Strings.mask("1234",3));
assert.equal("#2345", Strings.mask("12345",4));



assert.equal(0, Strings.vowelCount("xyz"))
assert.equal(0, Strings.vowelCount(""))
assert.equal(1, Strings.vowelCount("a"))
assert.equal(5, Strings.vowelCount("abracadabra"))

assert.deepEqual([], Strings.filterByLength([], 2))
assert.deepEqual(["aa"], Strings.filterByLength(["a", "aa"], 2))


console.log('Tests finished')
