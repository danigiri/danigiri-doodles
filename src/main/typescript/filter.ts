
class Tree<T> {
    
    children: Map<T,Tree<T>>;
    
    constructor() {
        this.children = new Map<T,Tree<T>>();
    }

    isLeaf():boolean {
        return this.children.size===0;
    }

    hasChild(k:T):boolean {
        return this.children.has(k);
    }

    getChild(k:T): Tree<T> {
        return this.children.get(k)!;
    }

    addChild(k:T): Tree<T> {
        const child = new Tree<T>();
        this.children.set(k,child);
        return child;
    }
}


export class PathFilter {


    // given a list of full non-empty paths, create a filesystem tree
    public root: Tree<string>;

    constructor(paths: string[]) {
        this.root = new Tree<string>();
        for (let path of paths) {
            const entries = path.split("/");
            // for each entry, navigate or create tree, starting with root
            let node = this.root;
            for (let i=1;i<entries.length;i++) {
                const entry = entries[i];
                if (node.hasChild(entry)) {
                    node = node.getChild(entry);
                } else {
                    node = node.addChild(entry);
                }
            }
        }
    }

    public hasPath(path: string):boolean {
        if (path===null || path===undefined || path.length === 0) {
            return false;
        }
        return this.hasPathR(this.root, path.split("/"),1);
    }

    public hasFile(path: string): boolean {
        if (path===null || path===undefined || path.length === 0) {
            return false;
        }
        return this.hasFileR(this.root, path.split("/"),1);
    }

    private hasPathR(node: Tree<string>, entries: string[], i:number):boolean{
        if (i === entries.length) {
            return true;
        }
        const entry = entries[i];
        return node.hasChild(entry) ? this.hasPathR(node.getChild(entry), entries, i+1): false;
    }


    private hasFileR(node: Tree<string>, entries: string[], i:number):boolean {
        if (i === entries.length) {
         return node.isLeaf();
        }
        const entry = entries[i];
        return node.hasChild(entry) ? this.hasFileR(node.getChild(entry), entries, i+1) : false;
    }

}

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

