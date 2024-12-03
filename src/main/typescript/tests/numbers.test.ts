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



assert.deepStrictEqual(Numbers.multiplicationTable(1), [[1]])
assert.deepStrictEqual(Numbers.multiplicationTable(2), [[1, 2], [2, 4]])
assert.deepStrictEqual(Numbers.multiplicationTable(3), [[1, 2, 3], [2, 4, 6], [3, 6, 9]])


console.log('Tests finished');

/*
 * Copyright 2024 Daniel Giribet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

