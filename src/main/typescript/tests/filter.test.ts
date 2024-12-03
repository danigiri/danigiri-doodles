


import { assert } from 'chai';

import {PathFilter} from '../filter';

const filter = new PathFilter(["/a/b","/a/c","/d"]);
assert.isTrue(filter.hasFile("/a/b"));
assert.isTrue(filter.hasFile("/a/c"));
assert.isTrue(filter.hasFile("/d"));
assert.isFalse(filter.hasFile("/x"))
assert.isFalse(filter.hasFile("/a/b/c"))
assert.isTrue(filter.hasPath("/a/b"));
assert.isTrue(filter.hasPath("/a"));

console.log('Tests finished')

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

