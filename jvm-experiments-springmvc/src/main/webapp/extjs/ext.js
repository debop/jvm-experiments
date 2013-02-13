/*
 Ext JS 4.1 - JavaScript Library
 Copyright (c) 2006-2012, Sencha Inc.
 All rights reserved.
 licensing@sencha.com

 http://www.sencha.com/license

 Open Source License
 ------------------------------------------------------------------------------------------
 This version of Ext JS is licensed under the terms of the Open Source GPL 3.0 license. 

 http://www.gnu.org/licenses/gpl.html

 There are several FLOSS exceptions available for use with this release for
 open source applications that are distributed under a license other than GPL.

 * Open Source License Exception for Applications

 http://www.sencha.com/products/floss-exception.php

 * Open Source License Exception for Development

 http://www.sencha.com/products/ux-exception.php


 Alternate Licensing
 ------------------------------------------------------------------------------------------
 Commercial and OEM Licenses are available for an alternate download of Ext JS.
 This is the appropriate option if you are creating proprietary applications and you are 
 not prepared to distribute and share the source code of your application under the 
 GPL v3 license. Please visit http://www.sencha.com/license for more details.

 --

 This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND NON-INFRINGEMENT OF THIRD-PARTY INTELLECTUAL PROPERTY RIGHTS.  See the GNU General Public License for more details.
 */
var Ext = Ext || {};
Ext._startTime = new Date().getTime();
(function () {
    var h = this, a = Object.prototype, j = a.toString, b = true, g = {toString: 1}, e = function () {
    }, d = function () {
        var i = d.caller.caller;
        return i.$owner.prototype[i.$name].apply(this, arguments)
    }, c;
    Ext.global = h;
    for (c in g) {
        b = null
    }
    if (b) {
        b = ["hasOwnProperty", "valueOf", "isPrototypeOf", "propertyIsEnumerable", "toLocaleString", "toString", "constructor"]
    }
    Ext.enumerables = b;
    Ext.apply = function (o, n, q) {
        if (q) {
            Ext.apply(o, q)
        }
        if (o && n && typeof n === "object") {
            var p, m, l;
            for (p in n) {
                o[p] = n[p]
            }
            if (b) {
                for (m = b.length; m--;) {
                    l = b[m];
                    if (n.hasOwnProperty(l)) {
                        o[l] = n[l]
                    }
                }
            }
        }
        return o
    };
    Ext.buildSettings = Ext.apply({baseCSSPrefix: "x-", scopeResetCSS: false}, Ext.buildSettings || {});
    Ext.apply(Ext, {name: Ext.sandboxName || "Ext", emptyFn: e, emptyString: new String(), baseCSSPrefix: Ext.buildSettings.baseCSSPrefix, applyIf: function (k, i) {
        var l;
        if (k) {
            for (l in i) {
                if (k[l] === undefined) {
                    k[l] = i[l]
                }
            }
        }
        return k
    }, iterate: function (i, l, k) {
        if (Ext.isEmpty(i)) {
            return
        }
        if (k === undefined) {
            k = i
        }
        if (Ext.isIterable(i)) {
            Ext.Array.each.call(Ext.Array, i, l, k)
        } else {
            Ext.Object.each.call(Ext.Object, i, l, k)
        }
    }});
    Ext.apply(Ext, {extend: (function () {
        var i = a.constructor, k = function (n) {
            for (var l in n) {
                if (!n.hasOwnProperty(l)) {
                    continue
                }
                this[l] = n[l]
            }
        };
        return function (l, q, o) {
            if (Ext.isObject(q)) {
                o = q;
                q = l;
                l = o.constructor !== i ? o.constructor : function () {
                    q.apply(this, arguments)
                }
            }
            var n = function () {
            }, m, p = q.prototype;
            n.prototype = p;
            m = l.prototype = new n();
            m.constructor = l;
            l.superclass = p;
            if (p.constructor === i) {
                p.constructor = q
            }
            l.override = function (r) {
                Ext.override(l, r)
            };
            m.override = k;
            m.proto = m;
            l.override(o);
            l.extend = function (r) {
                return Ext.extend(l, r)
            };
            return l
        }
    }()), override: function (m, n) {
        if (m.$isClass) {
            m.override(n)
        } else {
            if (typeof m == "function") {
                Ext.apply(m.prototype, n)
            } else {
                var i = m.self, k, l;
                if (i && i.$isClass) {
                    for (k in n) {
                        if (n.hasOwnProperty(k)) {
                            l = n[k];
                            if (typeof l == "function") {
                                l.$name = k;
                                l.$owner = i;
                                l.$previous = m.hasOwnProperty(k) ? m[k] : d
                            }
                            m[k] = l
                        }
                    }
                } else {
                    Ext.apply(m, n)
                }
            }
        }
        return m
    }});
    Ext.apply(Ext, {valueFrom: function (l, i, k) {
        return Ext.isEmpty(l, k) ? i : l
    }, typeOf: function (k) {
        var i, l;
        if (k === null) {
            return"null"
        }
        i = typeof k;
        if (i === "undefined" || i === "string" || i === "number" || i === "boolean") {
            return i
        }
        l = j.call(k);
        switch (l) {
            case"[object Array]":
                return"array";
            case"[object Date]":
                return"date";
            case"[object Boolean]":
                return"boolean";
            case"[object Number]":
                return"number";
            case"[object RegExp]":
                return"regexp"
        }
        if (i === "function") {
            return"function"
        }
        if (i === "object") {
            if (k.nodeType !== undefined) {
                if (k.nodeType === 3) {
                    return(/\S/).test(k.nodeValue) ? "textnode" : "whitespace"
                } else {
                    return"element"
                }
            }
            return"object"
        }
    }, isEmpty: function (i, k) {
        return(i === null) || (i === undefined) || (!k ? i === "" : false) || (Ext.isArray(i) && i.length === 0)
    }, isArray: ("isArray" in Array) ? Array.isArray : function (i) {
        return j.call(i) === "[object Array]"
    }, isDate: function (i) {
        return j.call(i) === "[object Date]"
    }, isObject: (j.call(null) === "[object Object]") ? function (i) {
        return i !== null && i !== undefined && j.call(i) === "[object Object]" && i.ownerDocument === undefined
    } : function (i) {
        return j.call(i) === "[object Object]"
    }, isSimpleObject: function (i) {
        return i instanceof Object && i.constructor === Object
    }, isPrimitive: function (k) {
        var i = typeof k;
        return i === "string" || i === "number" || i === "boolean"
    }, isFunction: (typeof document !== "undefined" && typeof document.getElementsByTagName("body") === "function") ? function (i) {
        return j.call(i) === "[object Function]"
    } : function (i) {
        return typeof i === "function"
    }, isNumber: function (i) {
        return typeof i === "number" && isFinite(i)
    }, isNumeric: function (i) {
        return !isNaN(parseFloat(i)) && isFinite(i)
    }, isString: function (i) {
        return typeof i === "string"
    }, isBoolean: function (i) {
        return typeof i === "boolean"
    }, isElement: function (i) {
        return i ? i.nodeType === 1 : false
    }, isTextNode: function (i) {
        return i ? i.nodeName === "#text" : false
    }, isDefined: function (i) {
        return typeof i !== "undefined"
    }, isIterable: function (k) {
        var i = typeof k, l = false;
        if (k && i != "string") {
            if (i == "function") {
                if (Ext.isSafari) {
                    l = k instanceof NodeList || k instanceof HTMLCollection
                }
            } else {
                l = true
            }
        }
        return l ? k.length !== undefined : false
    }});
    Ext.apply(Ext, {clone: function (q) {
        var p, o, m, l, r, n;
        if (q === null || q === undefined) {
            return q
        }
        if (q.nodeType && q.cloneNode) {
            return q.cloneNode(true)
        }
        p = j.call(q);
        if (p === "[object Date]") {
            return new Date(q.getTime())
        }
        if (p === "[object Array]") {
            o = q.length;
            r = [];
            while (o--) {
                r[o] = Ext.clone(q[o])
            }
        } else {
            if (p === "[object Object]" && q.constructor === Object) {
                r = {};
                for (n in q) {
                    r[n] = Ext.clone(q[n])
                }
                if (b) {
                    for (m = b.length; m--;) {
                        l = b[m];
                        r[l] = q[l]
                    }
                }
            }
        }
        return r || q
    }, getUniqueGlobalNamespace: function () {
        var l = this.uniqueGlobalNamespace, k;
        if (l === undefined) {
            k = 0;
            do {
                l = "ExtBox" + (++k)
            } while (Ext.global[l] !== undefined);
            Ext.global[l] = Ext;
            this.uniqueGlobalNamespace = l
        }
        return l
    }, functionFactoryCache: {}, cacheableFunctionFactory: function () {
        var o = this, l = Array.prototype.slice.call(arguments), k = o.functionFactoryCache, i, m, n;
        if (Ext.isSandboxed) {
            n = l.length;
            if (n > 0) {
                n--;
                l[n] = "var Ext=window." + Ext.name + ";" + l[n]
            }
        }
        i = l.join("");
        m = k[i];
        if (!m) {
            m = Function.prototype.constructor.apply(Function.prototype, l);
            k[i] = m
        }
        return m
    }, functionFactory: function () {
        var l = this, i = Array.prototype.slice.call(arguments), k;
        if (Ext.isSandboxed) {
            k = i.length;
            if (k > 0) {
                k--;
                i[k] = "var Ext=window." + Ext.name + ";" + i[k]
            }
        }
        return Function.prototype.constructor.apply(Function.prototype, i)
    }, Logger: {verbose: e, log: e, info: e, warn: e, error: function (i) {
        throw new Error(i)
    }, deprecate: e}});
    Ext.type = Ext.typeOf
}());
Ext.globalEval = Ext.global.execScript ? function (a) {
    execScript(a)
} : function ($$code) {
    (function () {
        eval($$code)
    }())
};
(function () {
    var a = "4.1.1.1", b;
    Ext.Version = b = Ext.extend(Object, {constructor: function (c) {
        var e, d;
        if (c instanceof b) {
            return c
        }
        this.version = this.shortVersion = String(c).toLowerCase().replace(/_/g, ".").replace(/[\-+]/g, "");
        d = this.version.search(/([^\d\.])/);
        if (d !== -1) {
            this.release = this.version.substr(d, c.length);
            this.shortVersion = this.version.substr(0, d)
        }
        this.shortVersion = this.shortVersion.replace(/[^\d]/g, "");
        e = this.version.split(".");
        this.major = parseInt(e.shift() || 0, 10);
        this.minor = parseInt(e.shift() || 0, 10);
        this.patch = parseInt(e.shift() || 0, 10);
        this.build = parseInt(e.shift() || 0, 10);
        return this
    }, toString: function () {
        return this.version
    }, valueOf: function () {
        return this.version
    }, getMajor: function () {
        return this.major || 0
    }, getMinor: function () {
        return this.minor || 0
    }, getPatch: function () {
        return this.patch || 0
    }, getBuild: function () {
        return this.build || 0
    }, getRelease: function () {
        return this.release || ""
    }, isGreaterThan: function (c) {
        return b.compare(this.version, c) === 1
    }, isGreaterThanOrEqual: function (c) {
        return b.compare(this.version, c) >= 0
    }, isLessThan: function (c) {
        return b.compare(this.version, c) === -1
    }, isLessThanOrEqual: function (c) {
        return b.compare(this.version, c) <= 0
    }, equals: function (c) {
        return b.compare(this.version, c) === 0
    }, match: function (c) {
        c = String(c);
        return this.version.substr(0, c.length) === c
    }, toArray: function () {
        return[this.getMajor(), this.getMinor(), this.getPatch(), this.getBuild(), this.getRelease()]
    }, getShortVersion: function () {
        return this.shortVersion
    }, gt: function () {
        return this.isGreaterThan.apply(this, arguments)
    }, lt: function () {
        return this.isLessThan.apply(this, arguments)
    }, gtEq: function () {
        return this.isGreaterThanOrEqual.apply(this, arguments)
    }, ltEq: function () {
        return this.isLessThanOrEqual.apply(this, arguments)
    }});
    Ext.apply(b, {releaseValueMap: {dev: -6, alpha: -5, a: -5, beta: -4, b: -4, rc: -3, "#": -2, p: -1, pl: -1}, getComponentValue: function (c) {
        return !c ? 0 : (isNaN(c) ? this.releaseValueMap[c] || c : parseInt(c, 10))
    }, compare: function (h, g) {
        var d, e, c;
        h = new b(h).toArray();
        g = new b(g).toArray();
        for (c = 0; c < Math.max(h.length, g.length); c++) {
            d = this.getComponentValue(h[c]);
            e = this.getComponentValue(g[c]);
            if (d < e) {
                return -1
            } else {
                if (d > e) {
                    return 1
                }
            }
        }
        return 0
    }});
    Ext.apply(Ext, {versions: {}, lastRegisteredVersion: null, setVersion: function (d, c) {
        Ext.versions[d] = new b(c);
        Ext.lastRegisteredVersion = Ext.versions[d];
        return this
    }, getVersion: function (c) {
        if (c === undefined) {
            return Ext.lastRegisteredVersion
        }
        return Ext.versions[c]
    }, deprecate: function (c, e, g, d) {
        if (b.compare(Ext.getVersion(c), e) < 1) {
            g.call(d)
        }
    }});
    Ext.setVersion("core", a)
}());
Ext.String = (function () {
    var i = /^[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u2028\u2029\u202f\u205f\u3000]+|[\x09\x0a\x0b\x0c\x0d\x20\xa0\u1680\u180e\u2000\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u2028\u2029\u202f\u205f\u3000]+$/g, m = /('|\\)/g, h = /\{(\d+)\}/g, b = /([-.*+?\^${}()|\[\]\/\\])/g, n = /^\s+|\s+$/g, j = /\s+/, l = /(^[^a-z]*|[^\w])/gi, d, a, g, c, e = function (p, o) {
        return d[o]
    }, k = function (p, o) {
        return(o in a) ? a[o] : String.fromCharCode(parseInt(o.substr(2), 10))
    };
    return{createVarName: function (o) {
        return o.replace(l, "")
    }, htmlEncode: function (o) {
        return(!o) ? o : String(o).replace(g, e)
    }, htmlDecode: function (o) {
        return(!o) ? o : String(o).replace(c, k)
    }, addCharacterEntities: function (p) {
        var o = [], s = [], q, r;
        for (q in p) {
            r = p[q];
            a[q] = r;
            d[r] = q;
            o.push(r);
            s.push(q)
        }
        g = new RegExp("(" + o.join("|") + ")", "g");
        c = new RegExp("(" + s.join("|") + "|&#[0-9]{1,5};)", "g")
    }, resetCharacterEntities: function () {
        d = {};
        a = {};
        this.addCharacterEntities({"&amp;": "&", "&gt;": ">", "&lt;": "<", "&quot;": '"', "&#39;": "'"})
    }, urlAppend: function (p, o) {
        if (!Ext.isEmpty(o)) {
            return p + (p.indexOf("?") === -1 ? "?" : "&") + o
        }
        return p
    }, trim: function (o) {
        return o.replace(i, "")
    }, capitalize: function (o) {
        return o.charAt(0).toUpperCase() + o.substr(1)
    }, uncapitalize: function (o) {
        return o.charAt(0).toLowerCase() + o.substr(1)
    }, ellipsis: function (q, o, r) {
        if (q && q.length > o) {
            if (r) {
                var s = q.substr(0, o - 2), p = Math.max(s.lastIndexOf(" "), s.lastIndexOf("."), s.lastIndexOf("!"), s.lastIndexOf("?"));
                if (p !== -1 && p >= (o - 15)) {
                    return s.substr(0, p) + "..."
                }
            }
            return q.substr(0, o - 3) + "..."
        }
        return q
    }, escapeRegex: function (o) {
        return o.replace(b, "\\$1")
    }, escape: function (o) {
        return o.replace(m, "\\$1")
    }, toggle: function (p, q, o) {
        return p === q ? o : q
    }, leftPad: function (p, q, r) {
        var o = String(p);
        r = r || " ";
        while (o.length < q) {
            o = r + o
        }
        return o
    }, format: function (p) {
        var o = Ext.Array.toArray(arguments, 1);
        return p.replace(h, function (q, r) {
            return o[r]
        })
    }, repeat: function (s, r, p) {
        for (var o = [], q = r; q--;) {
            o.push(s)
        }
        return o.join(p || "")
    }, splitWords: function (o) {
        if (o && typeof o == "string") {
            return o.replace(n, "").split(j)
        }
        return o || []
    }}
}());
Ext.String.resetCharacterEntities();
Ext.htmlEncode = Ext.String.htmlEncode;
Ext.htmlDecode = Ext.String.htmlDecode;
Ext.urlAppend = Ext.String.urlAppend;
Ext.Number = new function () {
    var b = this, c = (0.9).toFixed() !== "1", a = Math;
    Ext.apply(this, {constrain: function (h, g, e) {
        var d = parseFloat(h);
        return(d < g) ? g : ((d > e) ? e : d)
    }, snap: function (h, e, g, i) {
        var d;
        if (h === undefined || h < g) {
            return g || 0
        }
        if (e) {
            d = h % e;
            if (d !== 0) {
                h -= d;
                if (d * 2 >= e) {
                    h += e
                } else {
                    if (d * 2 < -e) {
                        h -= e
                    }
                }
            }
        }
        return b.constrain(h, g, i)
    }, snapInRange: function (h, d, g, i) {
        var e;
        g = (g || 0);
        if (h === undefined || h < g) {
            return g
        }
        if (d && (e = ((h - g) % d))) {
            h -= e;
            e *= 2;
            if (e >= d) {
                h += d
            }
        }
        if (i !== undefined) {
            if (h > (i = b.snapInRange(i, d, g))) {
                h = i
            }
        }
        return h
    }, toFixed: c ? function (g, d) {
        d = d || 0;
        var e = a.pow(10, d);
        return(a.round(g * e) / e).toFixed(d)
    } : function (e, d) {
        return e.toFixed(d)
    }, from: function (e, d) {
        if (isFinite(e)) {
            e = parseFloat(e)
        }
        return !isNaN(e) ? e : d
    }, randomInt: function (e, d) {
        return a.floor(a.random() * (d - e + 1) + e)
    }});
    Ext.num = function () {
        return b.from.apply(this, arguments)
    }
};
(function () {
    var g = Array.prototype, o = g.slice, q = (function () {
        var A = [], e, z = 20;
        if (!A.splice) {
            return false
        }
        while (z--) {
            A.push("A")
        }
        A.splice(15, 0, "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F");
        e = A.length;
        A.splice(13, 0, "XXX");
        if (e + 1 != A.length) {
            return false
        }
        return true
    }()), j = "forEach" in g, u = "map" in g, p = "indexOf" in g, y = "every" in g, c = "some" in g, d = "filter" in g, n = (function () {
        var e = [1, 2, 3, 4, 5].sort(function () {
            return 0
        });
        return e[0] === 1 && e[1] === 2 && e[2] === 3 && e[3] === 4 && e[4] === 5
    }()), k = true, a, w, t, v;
    try {
        if (typeof document !== "undefined") {
            o.call(document.getElementsByTagName("body"))
        }
    } catch (s) {
        k = false
    }
    function m(z, e) {
        return(e < 0) ? Math.max(0, z.length + e) : Math.min(z.length, e)
    }

    function x(G, F, z, J) {
        var K = J ? J.length : 0, B = G.length, H = m(G, F), E, I, A, e, C, D;
        if (H === B) {
            if (K) {
                G.push.apply(G, J)
            }
        } else {
            E = Math.min(z, B - H);
            I = H + E;
            A = I + K - E;
            e = B - I;
            C = B - E;
            if (A < I) {
                for (D = 0; D < e; ++D) {
                    G[A + D] = G[I + D]
                }
            } else {
                if (A > I) {
                    for (D = e; D--;) {
                        G[A + D] = G[I + D]
                    }
                }
            }
            if (K && H === C) {
                G.length = C;
                G.push.apply(G, J)
            } else {
                G.length = C + K;
                for (D = 0; D < K; ++D) {
                    G[H + D] = J[D]
                }
            }
        }
        return G
    }

    function i(B, e, A, z) {
        if (z && z.length) {
            if (e < B.length) {
                B.splice.apply(B, [e, A].concat(z))
            } else {
                B.push.apply(B, z)
            }
        } else {
            B.splice(e, A)
        }
        return B
    }

    function b(A, e, z) {
        return x(A, e, z)
    }

    function r(A, e, z) {
        A.splice(e, z);
        return A
    }

    function l(C, e, A) {
        var B = m(C, e), z = C.slice(e, m(C, B + A));
        if (arguments.length < 4) {
            x(C, B, A)
        } else {
            x(C, B, A, o.call(arguments, 3))
        }
        return z
    }

    function h(e) {
        return e.splice.apply(e, o.call(arguments, 1))
    }

    w = q ? r : b;
    t = q ? i : x;
    v = q ? h : l;
    a = Ext.Array = {each: function (D, B, A, e) {
        D = a.from(D);
        var z, C = D.length;
        if (e !== true) {
            for (z = 0; z < C; z++) {
                if (B.call(A || D[z], D[z], z, D) === false) {
                    return z
                }
            }
        } else {
            for (z = C - 1; z > -1; z--) {
                if (B.call(A || D[z], D[z], z, D) === false) {
                    return z
                }
            }
        }
        return true
    }, forEach: j ? function (A, z, e) {
        return A.forEach(z, e)
    } : function (C, A, z) {
        var e = 0, B = C.length;
        for (; e < B; e++) {
            A.call(z, C[e], e, C)
        }
    }, indexOf: p ? function (A, e, z) {
        return A.indexOf(e, z)
    } : function (C, A, B) {
        var e, z = C.length;
        for (e = (B < 0) ? Math.max(0, z + B) : B || 0; e < z; e++) {
            if (C[e] === A) {
                return e
            }
        }
        return -1
    }, contains: p ? function (z, e) {
        return z.indexOf(e) !== -1
    } : function (B, A) {
        var e, z;
        for (e = 0, z = B.length; e < z; e++) {
            if (B[e] === A) {
                return true
            }
        }
        return false
    }, toArray: function (A, C, e) {
        if (!A || !A.length) {
            return[]
        }
        if (typeof A === "string") {
            A = A.split("")
        }
        if (k) {
            return o.call(A, C || 0, e || A.length)
        }
        var B = [], z;
        C = C || 0;
        e = e ? ((e < 0) ? A.length + e : e) : A.length;
        for (z = C; z < e; z++) {
            B.push(A[z])
        }
        return B
    }, pluck: function (D, e) {
        var z = [], A, C, B;
        for (A = 0, C = D.length; A < C; A++) {
            B = D[A];
            z.push(B[e])
        }
        return z
    }, map: u ? function (A, z, e) {
        return A.map(z, e)
    } : function (D, C, B) {
        var A = [], z = 0, e = D.length;
        for (; z < e; z++) {
            A[z] = C.call(B, D[z], z, D)
        }
        return A
    }, every: y ? function (A, z, e) {
        return A.every(z, e)
    } : function (C, A, z) {
        var e = 0, B = C.length;
        for (; e < B; ++e) {
            if (!A.call(z, C[e], e, C)) {
                return false
            }
        }
        return true
    }, some: c ? function (A, z, e) {
        return A.some(z, e)
    } : function (C, A, z) {
        var e = 0, B = C.length;
        for (; e < B; ++e) {
            if (A.call(z, C[e], e, C)) {
                return true
            }
        }
        return false
    }, clean: function (C) {
        var z = [], e = 0, B = C.length, A;
        for (; e < B; e++) {
            A = C[e];
            if (!Ext.isEmpty(A)) {
                z.push(A)
            }
        }
        return z
    }, unique: function (C) {
        var B = [], e = 0, A = C.length, z;
        for (; e < A; e++) {
            z = C[e];
            if (a.indexOf(B, z) === -1) {
                B.push(z)
            }
        }
        return B
    }, filter: d ? function (A, z, e) {
        return A.filter(z, e)
    } : function (D, B, A) {
        var z = [], e = 0, C = D.length;
        for (; e < C; e++) {
            if (B.call(A, D[e], e, D)) {
                z.push(D[e])
            }
        }
        return z
    }, from: function (A, z) {
        if (A === undefined || A === null) {
            return[]
        }
        if (Ext.isArray(A)) {
            return(z) ? o.call(A) : A
        }
        var e = typeof A;
        if (A && A.length !== undefined && e !== "string" && (e !== "function" || !A.apply)) {
            return a.toArray(A)
        }
        return[A]
    }, remove: function (A, z) {
        var e = a.indexOf(A, z);
        if (e !== -1) {
            w(A, e, 1)
        }
        return A
    }, include: function (z, e) {
        if (!a.contains(z, e)) {
            z.push(e)
        }
    }, clone: function (e) {
        return o.call(e)
    }, merge: function () {
        var e = o.call(arguments), B = [], z, A;
        for (z = 0, A = e.length; z < A; z++) {
            B = B.concat(e[z])
        }
        return a.unique(B)
    }, intersect: function () {
        var e = [], A = o.call(arguments), L, J, F, I, M, B, z, H, K, C, G, E, D;
        if (!A.length) {
            return e
        }
        L = A.length;
        for (G = M = 0; G < L; G++) {
            B = A[G];
            if (!I || B.length < I.length) {
                I = B;
                M = G
            }
        }
        I = a.unique(I);
        w(A, M, 1);
        z = I.length;
        L = A.length;
        for (G = 0; G < z; G++) {
            H = I[G];
            C = 0;
            for (E = 0; E < L; E++) {
                J = A[E];
                F = J.length;
                for (D = 0; D < F; D++) {
                    K = J[D];
                    if (H === K) {
                        C++;
                        break
                    }
                }
            }
            if (C === L) {
                e.push(H)
            }
        }
        return e
    }, difference: function (z, e) {
        var E = o.call(z), C = E.length, B, A, D;
        for (B = 0, D = e.length; B < D; B++) {
            for (A = 0; A < C; A++) {
                if (E[A] === e[B]) {
                    w(E, A, 1);
                    A--;
                    C--
                }
            }
        }
        return E
    }, slice: ([1, 2].slice(1, undefined).length ? function (A, z, e) {
        return o.call(A, z, e)
    } : function (A, z, e) {
        if (typeof z === "undefined") {
            return o.call(A)
        }
        if (typeof e === "undefined") {
            return o.call(A, z)
        }
        return o.call(A, z, e)
    }), sort: n ? function (z, e) {
        if (e) {
            return z.sort(e)
        } else {
            return z.sort()
        }
    } : function (F, E) {
        var C = F.length, B = 0, D, e, A, z;
        for (; B < C; B++) {
            A = B;
            for (e = B + 1; e < C; e++) {
                if (E) {
                    D = E(F[e], F[A]);
                    if (D < 0) {
                        A = e
                    }
                } else {
                    if (F[e] < F[A]) {
                        A = e
                    }
                }
            }
            if (A !== B) {
                z = F[B];
                F[B] = F[A];
                F[A] = z
            }
        }
        return F
    }, flatten: function (A) {
        var z = [];

        function e(B) {
            var D, E, C;
            for (D = 0, E = B.length; D < E; D++) {
                C = B[D];
                if (Ext.isArray(C)) {
                    e(C)
                } else {
                    z.push(C)
                }
            }
            return z
        }

        return e(A)
    }, min: function (D, C) {
        var z = D[0], e, B, A;
        for (e = 0, B = D.length; e < B; e++) {
            A = D[e];
            if (C) {
                if (C(z, A) === 1) {
                    z = A
                }
            } else {
                if (A < z) {
                    z = A
                }
            }
        }
        return z
    }, max: function (D, C) {
        var e = D[0], z, B, A;
        for (z = 0, B = D.length; z < B; z++) {
            A = D[z];
            if (C) {
                if (C(e, A) === -1) {
                    e = A
                }
            } else {
                if (A > e) {
                    e = A
                }
            }
        }
        return e
    }, mean: function (e) {
        return e.length > 0 ? a.sum(e) / e.length : undefined
    }, sum: function (C) {
        var z = 0, e, B, A;
        for (e = 0, B = C.length; e < B; e++) {
            A = C[e];
            z += A
        }
        return z
    }, toMap: function (C, e, A) {
        var B = {}, z = C.length;
        if (!e) {
            while (z--) {
                B[C[z]] = z + 1
            }
        } else {
            if (typeof e == "string") {
                while (z--) {
                    B[C[z][e]] = z + 1
                }
            } else {
                while (z--) {
                    B[e.call(A, C[z])] = z + 1
                }
            }
        }
        return B
    }, erase: w, insert: function (A, z, e) {
        return t(A, z, 0, e)
    }, replace: t, splice: v, push: function (B) {
        var e = arguments.length, A = 1, z;
        if (B === undefined) {
            B = []
        } else {
            if (!Ext.isArray(B)) {
                B = [B]
            }
        }
        for (; A < e; A++) {
            z = arguments[A];
            Array.prototype.push[Ext.isArray(z) ? "apply" : "call"](B, z)
        }
        return B
    }};
    Ext.each = a.each;
    a.union = a.merge;
    Ext.min = a.min;
    Ext.max = a.max;
    Ext.sum = a.sum;
    Ext.mean = a.mean;
    Ext.flatten = a.flatten;
    Ext.clean = a.clean;
    Ext.unique = a.unique;
    Ext.pluck = a.pluck;
    Ext.toArray = function () {
        return a.toArray.apply(a, arguments)
    }
}());
Ext.Function = {flexSetter: function (a) {
    return function (d, c) {
        var e, g;
        if (d === null) {
            return this
        }
        if (typeof d !== "string") {
            for (e in d) {
                if (d.hasOwnProperty(e)) {
                    a.call(this, e, d[e])
                }
            }
            if (Ext.enumerables) {
                for (g = Ext.enumerables.length; g--;) {
                    e = Ext.enumerables[g];
                    if (d.hasOwnProperty(e)) {
                        a.call(this, e, d[e])
                    }
                }
            }
        } else {
            a.call(this, d, c)
        }
        return this
    }
}, bind: function (d, c, b, a) {
    if (arguments.length === 2) {
        return function () {
            return d.apply(c, arguments)
        }
    }
    var g = d, e = Array.prototype.slice;
    return function () {
        var h = b || arguments;
        if (a === true) {
            h = e.call(arguments, 0);
            h = h.concat(b)
        } else {
            if (typeof a == "number") {
                h = e.call(arguments, 0);
                Ext.Array.insert(h, a, b)
            }
        }
        return g.apply(c || Ext.global, h)
    }
}, pass: function (c, a, b) {
    if (!Ext.isArray(a)) {
        if (Ext.isIterable(a)) {
            a = Ext.Array.clone(a)
        } else {
            a = a !== undefined ? [a] : []
        }
    }
    return function () {
        var d = [].concat(a);
        d.push.apply(d, arguments);
        return c.apply(b || this, d)
    }
}, alias: function (b, a) {
    return function () {
        return b[a].apply(b, arguments)
    }
}, clone: function (a) {
    return function () {
        return a.apply(this, arguments)
    }
}, createInterceptor: function (d, c, b, a) {
    var e = d;
    if (!Ext.isFunction(c)) {
        return d
    } else {
        return function () {
            var h = this, g = arguments;
            c.target = h;
            c.method = d;
            return(c.apply(b || h || Ext.global, g) !== false) ? d.apply(h || Ext.global, g) : a || null
        }
    }
}, createDelayed: function (e, c, d, b, a) {
    if (d || b) {
        e = Ext.Function.bind(e, d, b, a)
    }
    return function () {
        var h = this, g = Array.prototype.slice.call(arguments);
        setTimeout(function () {
            e.apply(h, g)
        }, c)
    }
}, defer: function (e, c, d, b, a) {
    e = Ext.Function.bind(e, d, b, a);
    if (c > 0) {
        return setTimeout(Ext.supports.TimeoutActualLateness ? function () {
            e()
        } : e, c)
    }
    e();
    return 0
}, createSequence: function (b, c, a) {
    if (!c) {
        return b
    } else {
        return function () {
            var d = b.apply(this, arguments);
            c.apply(a || this, arguments);
            return d
        }
    }
}, createBuffered: function (e, b, d, c) {
    var a;
    return function () {
        var h = c || Array.prototype.slice.call(arguments, 0), g = d || this;
        if (a) {
            clearTimeout(a)
        }
        a = setTimeout(function () {
            e.apply(g, h)
        }, b)
    }
}, createThrottled: function (e, b, d) {
    var g, a, c, i, h = function () {
        e.apply(d || this, c);
        g = new Date().getTime()
    };
    return function () {
        a = new Date().getTime() - g;
        c = arguments;
        clearTimeout(i);
        if (!g || (a >= b)) {
            h()
        } else {
            i = setTimeout(h, b - a)
        }
    }
}, interceptBefore: function (b, a, d, c) {
    var e = b[a] || Ext.emptyFn;
    return(b[a] = function () {
        var g = d.apply(c || this, arguments);
        e.apply(this, arguments);
        return g
    })
}, interceptAfter: function (b, a, d, c) {
    var e = b[a] || Ext.emptyFn;
    return(b[a] = function () {
        e.apply(this, arguments);
        return d.apply(c || this, arguments)
    })
}};
Ext.defer = Ext.Function.alias(Ext.Function, "defer");
Ext.pass = Ext.Function.alias(Ext.Function, "pass");
Ext.bind = Ext.Function.alias(Ext.Function, "bind");
(function () {
    var a = function () {
    }, b = Ext.Object = {chain: function (d) {
        a.prototype = d;
        var c = new a();
        a.prototype = null;
        return c
    }, toQueryObjects: function (e, k, d) {
        var c = b.toQueryObjects, j = [], g, h;
        if (Ext.isArray(k)) {
            for (g = 0, h = k.length; g < h; g++) {
                if (d) {
                    j = j.concat(c(e + "[" + g + "]", k[g], true))
                } else {
                    j.push({name: e, value: k[g]})
                }
            }
        } else {
            if (Ext.isObject(k)) {
                for (g in k) {
                    if (k.hasOwnProperty(g)) {
                        if (d) {
                            j = j.concat(c(e + "[" + g + "]", k[g], true))
                        } else {
                            j.push({name: e, value: k[g]})
                        }
                    }
                }
            } else {
                j.push({name: e, value: k})
            }
        }
        return j
    }, toQueryString: function (g, d) {
        var h = [], e = [], l, k, m, c, n;
        for (l in g) {
            if (g.hasOwnProperty(l)) {
                h = h.concat(b.toQueryObjects(l, g[l], d))
            }
        }
        for (k = 0, m = h.length; k < m; k++) {
            c = h[k];
            n = c.value;
            if (Ext.isEmpty(n)) {
                n = ""
            } else {
                if (Ext.isDate(n)) {
                    n = Ext.Date.toString(n)
                }
            }
            e.push(encodeURIComponent(c.name) + "=" + encodeURIComponent(String(n)))
        }
        return e.join("&")
    }, fromQueryString: function (d, r) {
        var m = d.replace(/^\?/, "").split("&"), u = {}, s, k, w, n, q, g, o, p, c, h, t, l, v, e;
        for (q = 0, g = m.length; q < g; q++) {
            o = m[q];
            if (o.length > 0) {
                k = o.split("=");
                w = decodeURIComponent(k[0]);
                n = (k[1] !== undefined) ? decodeURIComponent(k[1]) : "";
                if (!r) {
                    if (u.hasOwnProperty(w)) {
                        if (!Ext.isArray(u[w])) {
                            u[w] = [u[w]]
                        }
                        u[w].push(n)
                    } else {
                        u[w] = n
                    }
                } else {
                    h = w.match(/(\[):?([^\]]*)\]/g);
                    t = w.match(/^([^\[]+)/);
                    w = t[0];
                    l = [];
                    if (h === null) {
                        u[w] = n;
                        continue
                    }
                    for (p = 0, c = h.length; p < c; p++) {
                        v = h[p];
                        v = (v.length === 2) ? "" : v.substring(1, v.length - 1);
                        l.push(v)
                    }
                    l.unshift(w);
                    s = u;
                    for (p = 0, c = l.length; p < c; p++) {
                        v = l[p];
                        if (p === c - 1) {
                            if (Ext.isArray(s) && v === "") {
                                s.push(n)
                            } else {
                                s[v] = n
                            }
                        } else {
                            if (s[v] === undefined || typeof s[v] === "string") {
                                e = l[p + 1];
                                s[v] = (Ext.isNumeric(e) || e === "") ? [] : {}
                            }
                            s = s[v]
                        }
                    }
                }
            }
        }
        return u
    }, each: function (c, e, d) {
        for (var g in c) {
            if (c.hasOwnProperty(g)) {
                if (e.call(d || c, g, c[g], c) === false) {
                    return
                }
            }
        }
    }, merge: function (k) {
        var h = 1, j = arguments.length, c = b.merge, e = Ext.clone, g, m, l, d;
        for (; h < j; h++) {
            g = arguments[h];
            for (m in g) {
                l = g[m];
                if (l && l.constructor === Object) {
                    d = k[m];
                    if (d && d.constructor === Object) {
                        c(d, l)
                    } else {
                        k[m] = e(l)
                    }
                } else {
                    k[m] = l
                }
            }
        }
        return k
    }, mergeIf: function (c) {
        var h = 1, j = arguments.length, e = Ext.clone, d, g, k;
        for (; h < j; h++) {
            d = arguments[h];
            for (g in d) {
                if (!(g in c)) {
                    k = d[g];
                    if (k && k.constructor === Object) {
                        c[g] = e(k)
                    } else {
                        c[g] = k
                    }
                }
            }
        }
        return c
    }, getKey: function (c, e) {
        for (var d in c) {
            if (c.hasOwnProperty(d) && c[d] === e) {
                return d
            }
        }
        return null
    }, getValues: function (d) {
        var c = [], e;
        for (e in d) {
            if (d.hasOwnProperty(e)) {
                c.push(d[e])
            }
        }
        return c
    }, getKeys: (typeof Object.keys == "function") ? function (c) {
        if (!c) {
            return[]
        }
        return Object.keys(c)
    } : function (c) {
        var d = [], e;
        for (e in c) {
            if (c.hasOwnProperty(e)) {
                d.push(e)
            }
        }
        return d
    }, getSize: function (c) {
        var d = 0, e;
        for (e in c) {
            if (c.hasOwnProperty(e)) {
                d++
            }
        }
        return d
    }, classify: function (g) {
        var e = g, i = [], d = {}, c = function () {
            var k = 0, l = i.length, m;
            for (; k < l; k++) {
                m = i[k];
                this[m] = new d[m]()
            }
        }, h, j;
        for (h in g) {
            if (g.hasOwnProperty(h)) {
                j = g[h];
                if (j && j.constructor === Object) {
                    i.push(h);
                    d[h] = b.classify(j)
                }
            }
        }
        c.prototype = e;
        return c
    }};
    Ext.merge = Ext.Object.merge;
    Ext.mergeIf = Ext.Object.mergeIf;
    Ext.urlEncode = function () {
        var c = Ext.Array.from(arguments), d = "";
        if ((typeof c[1] === "string")) {
            d = c[1] + "&";
            c[1] = false
        }
        return d + b.toQueryString.apply(b, c)
    };
    Ext.urlDecode = function () {
        return b.fromQueryString.apply(b, arguments)
    }
}());
(function () {
    function b(d) {
        var c = Array.prototype.slice.call(arguments, 1);
        return d.replace(/\{(\d+)\}/g, function (e, g) {
            return c[g]
        })
    }

    Ext.Date = {now: Date.now || function () {
        return +new Date()
    }, toString: function (c) {
        var d = Ext.String.leftPad;
        return c.getFullYear() + "-" + d(c.getMonth() + 1, 2, "0") + "-" + d(c.getDate(), 2, "0") + "T" + d(c.getHours(), 2, "0") + ":" + d(c.getMinutes(), 2, "0") + ":" + d(c.getSeconds(), 2, "0")
    }, getElapsed: function (d, c) {
        return Math.abs(d - (c || new Date()))
    }, useStrict: false, formatCodeToRegex: function (d, c) {
        var e = a.parseCodes[d];
        if (e) {
            e = typeof e == "function" ? e() : e;
            a.parseCodes[d] = e
        }
        return e ? Ext.applyIf({c: e.c ? b(e.c, c || "{0}") : e.c}, e) : {g: 0, c: null, s: Ext.String.escapeRegex(d)}
    }, parseFunctions: {MS: function (d, c) {
        var e = new RegExp("\\/Date\\(([-+])?(\\d+)(?:[+-]\\d{4})?\\)\\/"), g = (d || "").match(e);
        return g ? new Date(((g[1] || "") + g[2]) * 1) : null
    }}, parseRegexes: [], formatFunctions: {MS: function () {
        return"\\/Date(" + this.getTime() + ")\\/"
    }}, y2kYear: 50, MILLI: "ms", SECOND: "s", MINUTE: "mi", HOUR: "h", DAY: "d", MONTH: "mo", YEAR: "y", defaults: {}, dayNames: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"], monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], monthNumbers: {January: 0, Jan: 0, February: 1, Feb: 1, March: 2, Mar: 2, April: 3, Apr: 3, May: 4, June: 5, Jun: 5, July: 6, Jul: 6, August: 7, Aug: 7, September: 8, Sep: 8, October: 9, Oct: 9, November: 10, Nov: 10, December: 11, Dec: 11}, defaultFormat: "m/d/Y", getShortMonthName: function (c) {
        return Ext.Date.monthNames[c].substring(0, 3)
    }, getShortDayName: function (c) {
        return Ext.Date.dayNames[c].substring(0, 3)
    }, getMonthNumber: function (c) {
        return Ext.Date.monthNumbers[c.substring(0, 1).toUpperCase() + c.substring(1, 3).toLowerCase()]
    }, formatContainsHourInfo: (function () {
        var d = /(\\.)/g, c = /([gGhHisucUOPZ]|MS)/;
        return function (e) {
            return c.test(e.replace(d, ""))
        }
    }()), formatContainsDateInfo: (function () {
        var d = /(\\.)/g, c = /([djzmnYycU]|MS)/;
        return function (e) {
            return c.test(e.replace(d, ""))
        }
    }()), unescapeFormat: (function () {
        var c = /\\/gi;
        return function (d) {
            return d.replace(c, "")
        }
    }()), formatCodes: {d: "Ext.String.leftPad(this.getDate(), 2, '0')", D: "Ext.Date.getShortDayName(this.getDay())", j: "this.getDate()", l: "Ext.Date.dayNames[this.getDay()]", N: "(this.getDay() ? this.getDay() : 7)", S: "Ext.Date.getSuffix(this)", w: "this.getDay()", z: "Ext.Date.getDayOfYear(this)", W: "Ext.String.leftPad(Ext.Date.getWeekOfYear(this), 2, '0')", F: "Ext.Date.monthNames[this.getMonth()]", m: "Ext.String.leftPad(this.getMonth() + 1, 2, '0')", M: "Ext.Date.getShortMonthName(this.getMonth())", n: "(this.getMonth() + 1)", t: "Ext.Date.getDaysInMonth(this)", L: "(Ext.Date.isLeapYear(this) ? 1 : 0)", o: "(this.getFullYear() + (Ext.Date.getWeekOfYear(this) == 1 && this.getMonth() > 0 ? +1 : (Ext.Date.getWeekOfYear(this) >= 52 && this.getMonth() < 11 ? -1 : 0)))", Y: "Ext.String.leftPad(this.getFullYear(), 4, '0')", y: "('' + this.getFullYear()).substring(2, 4)", a: "(this.getHours() < 12 ? 'am' : 'pm')", A: "(this.getHours() < 12 ? 'AM' : 'PM')", g: "((this.getHours() % 12) ? this.getHours() % 12 : 12)", G: "this.getHours()", h: "Ext.String.leftPad((this.getHours() % 12) ? this.getHours() % 12 : 12, 2, '0')", H: "Ext.String.leftPad(this.getHours(), 2, '0')", i: "Ext.String.leftPad(this.getMinutes(), 2, '0')", s: "Ext.String.leftPad(this.getSeconds(), 2, '0')", u: "Ext.String.leftPad(this.getMilliseconds(), 3, '0')", O: "Ext.Date.getGMTOffset(this)", P: "Ext.Date.getGMTOffset(this, true)", T: "Ext.Date.getTimezone(this)", Z: "(this.getTimezoneOffset() * -60)", c: function () {
        var k, h, g, d, j;
        for (k = "Y-m-dTH:i:sP", h = [], g = 0, d = k.length; g < d; ++g) {
            j = k.charAt(g);
            h.push(j == "T" ? "'T'" : a.getFormatCode(j))
        }
        return h.join(" + ")
    }, U: "Math.round(this.getTime() / 1000)"}, isValid: function (o, c, n, k, g, j, e) {
        k = k || 0;
        g = g || 0;
        j = j || 0;
        e = e || 0;
        var l = a.add(new Date(o < 100 ? 100 : o, c - 1, n, k, g, j, e), a.YEAR, o < 100 ? o - 100 : 0);
        return o == l.getFullYear() && c == l.getMonth() + 1 && n == l.getDate() && k == l.getHours() && g == l.getMinutes() && j == l.getSeconds() && e == l.getMilliseconds()
    }, parse: function (d, g, c) {
        var e = a.parseFunctions;
        if (e[g] == null) {
            a.createParser(g)
        }
        return e[g](d, Ext.isDefined(c) ? c : a.useStrict)
    }, parseDate: function (d, e, c) {
        return a.parse(d, e, c)
    }, getFormatCode: function (d) {
        var c = a.formatCodes[d];
        if (c) {
            c = typeof c == "function" ? c() : c;
            a.formatCodes[d] = c
        }
        return c || ("'" + Ext.String.escape(d) + "'")
    }, createFormat: function (h) {
        var g = [], c = false, e = "", d;
        for (d = 0; d < h.length; ++d) {
            e = h.charAt(d);
            if (!c && e == "\\") {
                c = true
            } else {
                if (c) {
                    c = false;
                    g.push("'" + Ext.String.escape(e) + "'")
                } else {
                    g.push(a.getFormatCode(e))
                }
            }
        }
        a.formatFunctions[h] = Ext.functionFactory("return " + g.join("+"))
    }, createParser: (function () {
        var c = ["var dt, y, m, d, h, i, s, ms, o, z, zz, u, v,", "def = Ext.Date.defaults,", "results = String(input).match(Ext.Date.parseRegexes[{0}]);", "if(results){", "{1}", "if(u != null){", "v = new Date(u * 1000);", "}else{", "dt = Ext.Date.clearTime(new Date);", "y = Ext.Number.from(y, Ext.Number.from(def.y, dt.getFullYear()));", "m = Ext.Number.from(m, Ext.Number.from(def.m - 1, dt.getMonth()));", "d = Ext.Number.from(d, Ext.Number.from(def.d, dt.getDate()));", "h  = Ext.Number.from(h, Ext.Number.from(def.h, dt.getHours()));", "i  = Ext.Number.from(i, Ext.Number.from(def.i, dt.getMinutes()));", "s  = Ext.Number.from(s, Ext.Number.from(def.s, dt.getSeconds()));", "ms = Ext.Number.from(ms, Ext.Number.from(def.ms, dt.getMilliseconds()));", "if(z >= 0 && y >= 0){", "v = Ext.Date.add(new Date(y < 100 ? 100 : y, 0, 1, h, i, s, ms), Ext.Date.YEAR, y < 100 ? y - 100 : 0);", "v = !strict? v : (strict === true && (z <= 364 || (Ext.Date.isLeapYear(v) && z <= 365))? Ext.Date.add(v, Ext.Date.DAY, z) : null);", "}else if(strict === true && !Ext.Date.isValid(y, m + 1, d, h, i, s, ms)){", "v = null;", "}else{", "v = Ext.Date.add(new Date(y < 100 ? 100 : y, m, d, h, i, s, ms), Ext.Date.YEAR, y < 100 ? y - 100 : 0);", "}", "}", "}", "if(v){", "if(zz != null){", "v = Ext.Date.add(v, Ext.Date.SECOND, -v.getTimezoneOffset() * 60 - zz);", "}else if(o){", "v = Ext.Date.add(v, Ext.Date.MINUTE, -v.getTimezoneOffset() + (sn == '+'? -1 : 1) * (hr * 60 + mn));", "}", "}", "return v;"].join("\n");
        return function (o) {
            var e = a.parseRegexes.length, p = 1, g = [], n = [], l = false, d = "", j = 0, k = o.length, m = [], h;
            for (; j < k; ++j) {
                d = o.charAt(j);
                if (!l && d == "\\") {
                    l = true
                } else {
                    if (l) {
                        l = false;
                        n.push(Ext.String.escape(d))
                    } else {
                        h = a.formatCodeToRegex(d, p);
                        p += h.g;
                        n.push(h.s);
                        if (h.g && h.c) {
                            if (h.calcAtEnd) {
                                m.push(h.c)
                            } else {
                                g.push(h.c)
                            }
                        }
                    }
                }
            }
            g = g.concat(m);
            a.parseRegexes[e] = new RegExp("^" + n.join("") + "$", "i");
            a.parseFunctions[o] = Ext.functionFactory("input", "strict", b(c, e, g.join("")))
        }
    }()), parseCodes: {d: {g: 1, c: "d = parseInt(results[{0}], 10);\n", s: "(3[0-1]|[1-2][0-9]|0[1-9])"}, j: {g: 1, c: "d = parseInt(results[{0}], 10);\n", s: "(3[0-1]|[1-2][0-9]|[1-9])"}, D: function () {
        for (var c = [], d = 0; d < 7; c.push(a.getShortDayName(d)), ++d) {
        }
        return{g: 0, c: null, s: "(?:" + c.join("|") + ")"}
    }, l: function () {
        return{g: 0, c: null, s: "(?:" + a.dayNames.join("|") + ")"}
    }, N: {g: 0, c: null, s: "[1-7]"}, S: {g: 0, c: null, s: "(?:st|nd|rd|th)"}, w: {g: 0, c: null, s: "[0-6]"}, z: {g: 1, c: "z = parseInt(results[{0}], 10);\n", s: "(\\d{1,3})"}, W: {g: 0, c: null, s: "(?:\\d{2})"}, F: function () {
        return{g: 1, c: "m = parseInt(Ext.Date.getMonthNumber(results[{0}]), 10);\n", s: "(" + a.monthNames.join("|") + ")"}
    }, M: function () {
        for (var c = [], d = 0; d < 12; c.push(a.getShortMonthName(d)), ++d) {
        }
        return Ext.applyIf({s: "(" + c.join("|") + ")"}, a.formatCodeToRegex("F"))
    }, m: {g: 1, c: "m = parseInt(results[{0}], 10) - 1;\n", s: "(1[0-2]|0[1-9])"}, n: {g: 1, c: "m = parseInt(results[{0}], 10) - 1;\n", s: "(1[0-2]|[1-9])"}, t: {g: 0, c: null, s: "(?:\\d{2})"}, L: {g: 0, c: null, s: "(?:1|0)"}, o: function () {
        return a.formatCodeToRegex("Y")
    }, Y: {g: 1, c: "y = parseInt(results[{0}], 10);\n", s: "(\\d{4})"}, y: {g: 1, c: "var ty = parseInt(results[{0}], 10);\ny = ty > Ext.Date.y2kYear ? 1900 + ty : 2000 + ty;\n", s: "(\\d{1,2})"}, a: {g: 1, c: "if (/(am)/i.test(results[{0}])) {\nif (!h || h == 12) { h = 0; }\n} else { if (!h || h < 12) { h = (h || 0) + 12; }}", s: "(am|pm|AM|PM)", calcAtEnd: true}, A: {g: 1, c: "if (/(am)/i.test(results[{0}])) {\nif (!h || h == 12) { h = 0; }\n} else { if (!h || h < 12) { h = (h || 0) + 12; }}", s: "(AM|PM|am|pm)", calcAtEnd: true}, g: {g: 1, c: "h = parseInt(results[{0}], 10);\n", s: "(1[0-2]|[0-9])"}, G: {g: 1, c: "h = parseInt(results[{0}], 10);\n", s: "(2[0-3]|1[0-9]|[0-9])"}, h: {g: 1, c: "h = parseInt(results[{0}], 10);\n", s: "(1[0-2]|0[1-9])"}, H: {g: 1, c: "h = parseInt(results[{0}], 10);\n", s: "(2[0-3]|[0-1][0-9])"}, i: {g: 1, c: "i = parseInt(results[{0}], 10);\n", s: "([0-5][0-9])"}, s: {g: 1, c: "s = parseInt(results[{0}], 10);\n", s: "([0-5][0-9])"}, u: {g: 1, c: "ms = results[{0}]; ms = parseInt(ms, 10)/Math.pow(10, ms.length - 3);\n", s: "(\\d+)"}, O: {g: 1, c: ["o = results[{0}];", "var sn = o.substring(0,1),", "hr = o.substring(1,3)*1 + Math.floor(o.substring(3,5) / 60),", "mn = o.substring(3,5) % 60;", "o = ((-12 <= (hr*60 + mn)/60) && ((hr*60 + mn)/60 <= 14))? (sn + Ext.String.leftPad(hr, 2, '0') + Ext.String.leftPad(mn, 2, '0')) : null;\n"].join("\n"), s: "([+-]\\d{4})"}, P: {g: 1, c: ["o = results[{0}];", "var sn = o.substring(0,1),", "hr = o.substring(1,3)*1 + Math.floor(o.substring(4,6) / 60),", "mn = o.substring(4,6) % 60;", "o = ((-12 <= (hr*60 + mn)/60) && ((hr*60 + mn)/60 <= 14))? (sn + Ext.String.leftPad(hr, 2, '0') + Ext.String.leftPad(mn, 2, '0')) : null;\n"].join("\n"), s: "([+-]\\d{2}:\\d{2})"}, T: {g: 0, c: null, s: "[A-Z]{1,4}"}, Z: {g: 1, c: "zz = results[{0}] * 1;\nzz = (-43200 <= zz && zz <= 50400)? zz : null;\n", s: "([+-]?\\d{1,5})"}, c: function () {
        var e = [], c = [a.formatCodeToRegex("Y", 1), a.formatCodeToRegex("m", 2), a.formatCodeToRegex("d", 3), a.formatCodeToRegex("H", 4), a.formatCodeToRegex("i", 5), a.formatCodeToRegex("s", 6), {c: "ms = results[7] || '0'; ms = parseInt(ms, 10)/Math.pow(10, ms.length - 3);\n"}, {c: ["if(results[8]) {", "if(results[8] == 'Z'){", "zz = 0;", "}else if (results[8].indexOf(':') > -1){", a.formatCodeToRegex("P", 8).c, "}else{", a.formatCodeToRegex("O", 8).c, "}", "}"].join("\n")}], g, d;
        for (g = 0, d = c.length; g < d; ++g) {
            e.push(c[g].c)
        }
        return{g: 1, c: e.join(""), s: [c[0].s, "(?:", "-", c[1].s, "(?:", "-", c[2].s, "(?:", "(?:T| )?", c[3].s, ":", c[4].s, "(?::", c[5].s, ")?", "(?:(?:\\.|,)(\\d+))?", "(Z|(?:[-+]\\d{2}(?::)?\\d{2}))?", ")?", ")?", ")?"].join("")}
    }, U: {g: 1, c: "u = parseInt(results[{0}], 10);\n", s: "(-?\\d+)"}}, dateFormat: function (c, d) {
        return a.format(c, d)
    }, isEqual: function (d, c) {
        if (d && c) {
            return(d.getTime() === c.getTime())
        }
        return !(d || c)
    }, format: function (d, e) {
        var c = a.formatFunctions;
        if (!Ext.isDate(d)) {
            return""
        }
        if (c[e] == null) {
            a.createFormat(e)
        }
        return c[e].call(d) + ""
    }, getTimezone: function (c) {
        return c.toString().replace(/^.* (?:\((.*)\)|([A-Z]{1,4})(?:[\-+][0-9]{4})?(?: -?\d+)?)$/, "$1$2").replace(/[^A-Z]/g, "")
    }, getGMTOffset: function (c, d) {
        var e = c.getTimezoneOffset();
        return(e > 0 ? "-" : "+") + Ext.String.leftPad(Math.floor(Math.abs(e) / 60), 2, "0") + (d ? ":" : "") + Ext.String.leftPad(Math.abs(e % 60), 2, "0")
    }, getDayOfYear: function (g) {
        var e = 0, j = Ext.Date.clone(g), c = g.getMonth(), h;
        for (h = 0, j.setDate(1), j.setMonth(0); h < c; j.setMonth(++h)) {
            e += a.getDaysInMonth(j)
        }
        return e + g.getDate() - 1
    }, getWeekOfYear: (function () {
        var c = 86400000, d = 7 * c;
        return function (g) {
            var h = Date.UTC(g.getFullYear(), g.getMonth(), g.getDate() + 3) / c, e = Math.floor(h / 7), i = new Date(e * d).getUTCFullYear();
            return e - Math.floor(Date.UTC(i, 0, 7) / d) + 1
        }
    }()), isLeapYear: function (c) {
        var d = c.getFullYear();
        return !!((d & 3) == 0 && (d % 100 || (d % 400 == 0 && d)))
    }, getFirstDayOfMonth: function (d) {
        var c = (d.getDay() - (d.getDate() - 1)) % 7;
        return(c < 0) ? (c + 7) : c
    }, getLastDayOfMonth: function (c) {
        return a.getLastDateOfMonth(c).getDay()
    }, getFirstDateOfMonth: function (c) {
        return new Date(c.getFullYear(), c.getMonth(), 1)
    }, getLastDateOfMonth: function (c) {
        return new Date(c.getFullYear(), c.getMonth(), a.getDaysInMonth(c))
    }, getDaysInMonth: (function () {
        var c = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        return function (e) {
            var d = e.getMonth();
            return d == 1 && a.isLeapYear(e) ? 29 : c[d]
        }
    }()), getSuffix: function (c) {
        switch (c.getDate()) {
            case 1:
            case 21:
            case 31:
                return"st";
            case 2:
            case 22:
                return"nd";
            case 3:
            case 23:
                return"rd";
            default:
                return"th"
        }
    }, clone: function (c) {
        return new Date(c.getTime())
    }, isDST: function (c) {
        return new Date(c.getFullYear(), 0, 1).getTimezoneOffset() != c.getTimezoneOffset()
    }, clearTime: function (e, j) {
        if (j) {
            return Ext.Date.clearTime(Ext.Date.clone(e))
        }
        var h = e.getDate(), g, i;
        e.setHours(0);
        e.setMinutes(0);
        e.setSeconds(0);
        e.setMilliseconds(0);
        if (e.getDate() != h) {
            for (g = 1, i = a.add(e, Ext.Date.HOUR, g); i.getDate() != h; g++, i = a.add(e, Ext.Date.HOUR, g)) {
            }
            e.setDate(h);
            e.setHours(i.getHours())
        }
        return e
    }, add: function (h, g, i) {
        var j = Ext.Date.clone(h), c = Ext.Date, e;
        if (!g || i === 0) {
            return j
        }
        switch (g.toLowerCase()) {
            case Ext.Date.MILLI:
                j.setMilliseconds(j.getMilliseconds() + i);
                break;
            case Ext.Date.SECOND:
                j.setSeconds(j.getSeconds() + i);
                break;
            case Ext.Date.MINUTE:
                j.setMinutes(j.getMinutes() + i);
                break;
            case Ext.Date.HOUR:
                j.setHours(j.getHours() + i);
                break;
            case Ext.Date.DAY:
                j.setDate(j.getDate() + i);
                break;
            case Ext.Date.MONTH:
                e = h.getDate();
                if (e > 28) {
                    e = Math.min(e, Ext.Date.getLastDateOfMonth(Ext.Date.add(Ext.Date.getFirstDateOfMonth(h), Ext.Date.MONTH, i)).getDate())
                }
                j.setDate(e);
                j.setMonth(h.getMonth() + i);
                break;
            case Ext.Date.YEAR:
                e = h.getDate();
                if (e > 28) {
                    e = Math.min(e, Ext.Date.getLastDateOfMonth(Ext.Date.add(Ext.Date.getFirstDateOfMonth(h), Ext.Date.YEAR, i)).getDate())
                }
                j.setDate(e);
                j.setFullYear(h.getFullYear() + i);
                break
        }
        return j
    }, between: function (d, g, c) {
        var e = d.getTime();
        return g.getTime() <= e && e <= c.getTime()
    }, compat: function () {
        var d = window.Date, c, l, j = ["useStrict", "formatCodeToRegex", "parseFunctions", "parseRegexes", "formatFunctions", "y2kYear", "MILLI", "SECOND", "MINUTE", "HOUR", "DAY", "MONTH", "YEAR", "defaults", "dayNames", "monthNames", "monthNumbers", "getShortMonthName", "getShortDayName", "getMonthNumber", "formatCodes", "isValid", "parseDate", "getFormatCode", "createFormat", "createParser", "parseCodes"], h = ["dateFormat", "format", "getTimezone", "getGMTOffset", "getDayOfYear", "getWeekOfYear", "isLeapYear", "getFirstDayOfMonth", "getLastDayOfMonth", "getDaysInMonth", "getSuffix", "clone", "isDST", "clearTime", "add", "between"], i = j.length, e = h.length, g, k, m;
        for (m = 0; m < i; m++) {
            g = j[m];
            d[g] = a[g]
        }
        for (c = 0; c < e; c++) {
            k = h[c];
            d.prototype[k] = function () {
                var n = Array.prototype.slice.call(arguments);
                n.unshift(this);
                return a[k].apply(a, n)
            }
        }
    }};
    var a = Ext.Date
}());
(function (a) {
    var c = [], b = function () {
    };
    Ext.apply(b, {$className: "Ext.Base", $isClass: true, create: function () {
        return Ext.create.apply(Ext, [this].concat(Array.prototype.slice.call(arguments, 0)))
    }, extend: function (j) {
        var d = j.prototype, m, g, h, k, e, l;
        g = this.prototype = Ext.Object.chain(d);
        g.self = this;
        this.superclass = g.superclass = d;
        if (!j.$isClass) {
            m = Ext.Base.prototype;
            for (h in m) {
                if (h in g) {
                    g[h] = m[h]
                }
            }
        }
        l = d.$inheritableStatics;
        if (l) {
            for (h = 0, k = l.length; h < k; h++) {
                e = l[h];
                if (!this.hasOwnProperty(e)) {
                    this[e] = j[e]
                }
            }
        }
        if (j.$onExtended) {
            this.$onExtended = j.$onExtended.slice()
        }
        g.config = new g.configClass();
        g.initConfigList = g.initConfigList.slice();
        g.initConfigMap = Ext.clone(g.initConfigMap);
        g.configMap = Ext.Object.chain(g.configMap)
    }, $onExtended: [], triggerExtended: function () {
        var g = this.$onExtended, e = g.length, d, h;
        if (e > 0) {
            for (d = 0; d < e; d++) {
                h = g[d];
                h.fn.apply(h.scope || this, arguments)
            }
        }
    }, onExtended: function (e, d) {
        this.$onExtended.push({fn: e, scope: d});
        return this
    }, addConfig: function (h, l) {
        var n = this.prototype, m = Ext.Class.configNameCache, i = n.configMap, j = n.initConfigList, g = n.initConfigMap, k = n.config, d, e, o;
        for (e in h) {
            if (h.hasOwnProperty(e)) {
                if (!i[e]) {
                    i[e] = true
                }
                o = h[e];
                d = m[e].initialized;
                if (!g[e] && o !== null && !n[d]) {
                    g[e] = true;
                    j.push(e)
                }
            }
        }
        if (l) {
            Ext.merge(k, h)
        } else {
            Ext.mergeIf(k, h)
        }
        n.configClass = Ext.Object.classify(k)
    }, addStatics: function (d) {
        var g, e;
        for (e in d) {
            if (d.hasOwnProperty(e)) {
                g = d[e];
                if (typeof g == "function" && !g.$isClass && g !== Ext.emptyFn && g !== Ext.identityFn) {
                    g.$owner = this;
                    g.$name = e
                }
                this[e] = g
            }
        }
        return this
    }, addInheritableStatics: function (e) {
        var i, d, h = this.prototype, g, j;
        i = h.$inheritableStatics;
        d = h.$hasInheritableStatics;
        if (!i) {
            i = h.$inheritableStatics = [];
            d = h.$hasInheritableStatics = {}
        }
        for (g in e) {
            if (e.hasOwnProperty(g)) {
                j = e[g];
                this[g] = j;
                if (!d[g]) {
                    d[g] = true;
                    i.push(g)
                }
            }
        }
        return this
    }, addMembers: function (e) {
        var h = this.prototype, d = Ext.enumerables, l = [], j, k, g, m;
        for (g in e) {
            l.push(g)
        }
        if (d) {
            l.push.apply(l, d)
        }
        for (j = 0, k = l.length; j < k; j++) {
            g = l[j];
            if (e.hasOwnProperty(g)) {
                m = e[g];
                if (typeof m == "function" && !m.$isClass && m !== Ext.emptyFn) {
                    m.$owner = this;
                    m.$name = g
                }
                h[g] = m
            }
        }
        return this
    }, addMember: function (d, e) {
        if (typeof e == "function" && !e.$isClass && e !== Ext.emptyFn) {
            e.$owner = this;
            e.$name = d
        }
        this.prototype[d] = e;
        return this
    }, implement: function () {
        this.addMembers.apply(this, arguments)
    }, borrow: function (j, g) {
        var n = this.prototype, m = j.prototype, h, k, e, l, d;
        g = Ext.Array.from(g);
        for (h = 0, k = g.length; h < k; h++) {
            e = g[h];
            d = m[e];
            if (typeof d == "function") {
                l = Ext.Function.clone(d);
                l.$owner = this;
                l.$name = e;
                n[e] = l
            } else {
                n[e] = d
            }
        }
        return this
    }, override: function (e) {
        var m = this, o = Ext.enumerables, k = m.prototype, h = Ext.Function.clone, d, j, g, n, l, i;
        if (arguments.length === 2) {
            d = e;
            e = {};
            e[d] = arguments[1];
            o = null
        }
        do {
            l = [];
            n = null;
            for (d in e) {
                if (d == "statics") {
                    n = e[d]
                } else {
                    if (d == "config") {
                        m.addConfig(e[d], true)
                    } else {
                        l.push(d)
                    }
                }
            }
            if (o) {
                l.push.apply(l, o)
            }
            for (j = l.length; j--;) {
                d = l[j];
                if (e.hasOwnProperty(d)) {
                    g = e[d];
                    if (typeof g == "function" && !g.$className && g !== Ext.emptyFn) {
                        if (typeof g.$owner != "undefined") {
                            g = h(g)
                        }
                        g.$owner = m;
                        g.$name = d;
                        i = k[d];
                        if (i) {
                            g.$previous = i
                        }
                    }
                    k[d] = g
                }
            }
            k = m;
            e = n
        } while (e);
        return this
    }, callParent: function (d) {
        var e;
        return(e = this.callParent.caller) && (e.$previous || ((e = e.$owner ? e : e.caller) && e.$owner.superclass.self[e.$name])).apply(this, d || c)
    }, callSuper: function (d) {
        var e;
        return(e = this.callSuper.caller) && ((e = e.$owner ? e : e.caller) && e.$owner.superclass.self[e.$name]).apply(this, d || c)
    }, mixin: function (g, i) {
        var d = i.prototype, e = this.prototype, h;
        if (typeof d.onClassMixedIn != "undefined") {
            d.onClassMixedIn.call(i, this)
        }
        if (!e.hasOwnProperty("mixins")) {
            if ("mixins" in e) {
                e.mixins = Ext.Object.chain(e.mixins)
            } else {
                e.mixins = {}
            }
        }
        for (h in d) {
            if (h === "mixins") {
                Ext.merge(e.mixins, d[h])
            } else {
                if (typeof e[h] == "undefined" && h != "mixinId" && h != "config") {
                    e[h] = d[h]
                }
            }
        }
        if ("config" in d) {
            this.addConfig(d.config, false)
        }
        e.mixins[g] = d
    }, getName: function () {
        return Ext.getClassName(this)
    }, createAlias: a(function (e, d) {
        this.override(e, function () {
            return this[d].apply(this, arguments)
        })
    }), addXtype: function (i) {
        var e = this.prototype, h = e.xtypesMap, g = e.xtypes, d = e.xtypesChain;
        if (!e.hasOwnProperty("xtypesMap")) {
            h = e.xtypesMap = Ext.merge({}, e.xtypesMap || {});
            g = e.xtypes = e.xtypes ? [].concat(e.xtypes) : [];
            d = e.xtypesChain = e.xtypesChain ? [].concat(e.xtypesChain) : [];
            e.xtype = i
        }
        if (!h[i]) {
            h[i] = true;
            g.push(i);
            d.push(i);
            Ext.ClassManager.setAlias(this, "widget." + i)
        }
        return this
    }});
    b.implement({isInstance: true, $className: "Ext.Base", configClass: Ext.emptyFn, initConfigList: [], configMap: {}, initConfigMap: {}, statics: function () {
        var e = this.statics.caller, d = this.self;
        if (!e) {
            return d
        }
        return e.$owner
    }, callParent: function (e) {
        var g, d = (g = this.callParent.caller) && (g.$previous || ((g = g.$owner ? g : g.caller) && g.$owner.superclass[g.$name]));
        return d.apply(this, e || c)
    }, callSuper: function (e) {
        var g, d = (g = this.callSuper.caller) && ((g = g.$owner ? g : g.caller) && g.$owner.superclass[g.$name]);
        return d.apply(this, e || c)
    }, self: b, constructor: function () {
        return this
    }, initConfig: function (g) {
        var m = g, l = Ext.Class.configNameCache, j = new this.configClass(), p = this.initConfigList, h = this.configMap, o, k, n, e, d;
        this.initConfig = Ext.emptyFn;
        this.initialConfig = m || {};
        this.config = g = (m) ? Ext.merge(j, g) : j;
        if (m) {
            p = p.slice();
            for (e in m) {
                if (h[e]) {
                    if (m[e] !== null) {
                        p.push(e);
                        this[l[e].initialized] = false
                    }
                }
            }
        }
        for (k = 0, n = p.length; k < n; k++) {
            e = p[k];
            o = l[e];
            d = o.initialized;
            if (!this[d]) {
                this[d] = true;
                this[o.set].call(this, g[e])
            }
        }
        return this
    }, hasConfig: function (d) {
        return Boolean(this.configMap[d])
    }, setConfig: function (h, l) {
        if (!h) {
            return this
        }
        var g = Ext.Class.configNameCache, d = this.config, k = this.configMap, j = this.initialConfig, e, i;
        l = Boolean(l);
        for (e in h) {
            if (l && j.hasOwnProperty(e)) {
                continue
            }
            i = h[e];
            d[e] = i;
            if (k[e]) {
                this[g[e].set](i)
            }
        }
        return this
    }, getConfig: function (e) {
        var d = Ext.Class.configNameCache;
        return this[d[e].get]()
    }, getInitialConfig: function (e) {
        var d = this.config;
        if (!e) {
            return d
        } else {
            return d[e]
        }
    }, onConfigUpdate: function (k, m, n) {
        var o = this.self, g, j, d, h, l, e;
        k = Ext.Array.from(k);
        n = n || this;
        for (g = 0, j = k.length; g < j; g++) {
            d = k[g];
            h = "update" + Ext.String.capitalize(d);
            l = this[h] || Ext.emptyFn;
            e = function () {
                l.apply(this, arguments);
                n[m].apply(n, arguments)
            };
            e.$name = h;
            e.$owner = o;
            this[h] = e
        }
    }, destroy: function () {
        this.destroy = Ext.emptyFn
    }});
    b.prototype.callOverridden = b.prototype.callParent;
    Ext.Base = b
}(Ext.Function.flexSetter));
(function () {
    var c, b = Ext.Base, g = [], e, d;
    for (e in b) {
        if (b.hasOwnProperty(e)) {
            g.push(e)
        }
    }
    d = g.length;
    function a(i) {
        function h() {
            return this.constructor.apply(this, arguments) || null
        }

        return h
    }

    Ext.Class = c = function (i, j, h) {
        if (typeof i != "function") {
            h = j;
            j = i;
            i = null
        }
        if (!j) {
            j = {}
        }
        i = c.create(i, j);
        c.process(i, j, h);
        return i
    };
    Ext.apply(c, {onBeforeCreated: function (i, j, h) {
        i.addMembers(j);
        h.onCreated.call(i, i)
    }, create: function (h, l) {
        var j, k;
        if (!h) {
            h = a()
        }
        for (k = 0; k < d; k++) {
            j = g[k];
            h[j] = b[j]
        }
        return h
    }, process: function (h, q, m) {
        var l = q.preprocessors || c.defaultPreprocessors, t = this.preprocessors, w = {onBeforeCreated: this.onBeforeCreated}, v = [], x, p, o, u, n, s, r, k;
        delete q.preprocessors;
        for (o = 0, u = l.length; o < u; o++) {
            x = l[o];
            if (typeof x == "string") {
                x = t[x];
                p = x.properties;
                if (p === true) {
                    v.push(x.fn)
                } else {
                    if (p) {
                        for (n = 0, s = p.length; n < s; n++) {
                            r = p[n];
                            if (q.hasOwnProperty(r)) {
                                v.push(x.fn);
                                break
                            }
                        }
                    }
                }
            } else {
                v.push(x)
            }
        }
        w.onCreated = m ? m : Ext.emptyFn;
        w.preprocessors = v;
        this.doProcess(h, q, w)
    }, doProcess: function (i, l, h) {
        var k = this, j = h.preprocessors.shift();
        if (!j) {
            h.onBeforeCreated.apply(k, arguments);
            return
        }
        if (j.call(k, i, l, h, k.doProcess) !== false) {
            k.doProcess(i, l, h)
        }
    }, preprocessors: {}, registerPreprocessor: function (i, l, j, h, k) {
        if (!h) {
            h = "last"
        }
        if (!j) {
            j = [i]
        }
        this.preprocessors[i] = {name: i, properties: j || false, fn: l};
        this.setDefaultPreprocessorPosition(i, h, k);
        return this
    }, getPreprocessor: function (h) {
        return this.preprocessors[h]
    }, getPreprocessors: function () {
        return this.preprocessors
    }, defaultPreprocessors: [], getDefaultPreprocessors: function () {
        return this.defaultPreprocessors
    }, setDefaultPreprocessors: function (h) {
        this.defaultPreprocessors = Ext.Array.from(h);
        return this
    }, setDefaultPreprocessorPosition: function (j, l, k) {
        var h = this.defaultPreprocessors, i;
        if (typeof l == "string") {
            if (l === "first") {
                h.unshift(j);
                return this
            } else {
                if (l === "last") {
                    h.push(j);
                    return this
                }
            }
            l = (l === "after") ? 1 : -1
        }
        i = Ext.Array.indexOf(h, k);
        if (i !== -1) {
            Ext.Array.splice(h, Math.max(0, i + l), 0, j)
        }
        return this
    }, configNameCache: {}, getConfigNameMap: function (j) {
        var i = this.configNameCache, k = i[j], h;
        if (!k) {
            h = j.charAt(0).toUpperCase() + j.substr(1);
            k = i[j] = {internal: j, initialized: "_is" + h + "Initialized", apply: "apply" + h, update: "update" + h, set: "set" + h, get: "get" + h, doSet: "doSet" + h, changeEvent: j.toLowerCase() + "change"}
        }
        return k
    }});
    c.registerPreprocessor("extend", function (j, n) {
        var m = Ext.Base, o = m.prototype, p = n.extend, l, h, k;
        delete n.extend;
        if (p && p !== Object) {
            l = p
        } else {
            l = m
        }
        h = l.prototype;
        if (!l.$isClass) {
            for (k in o) {
                if (!h[k]) {
                    h[k] = o[k]
                }
            }
        }
        j.extend(l);
        j.triggerExtended.apply(j, arguments);
        if (n.onClassExtended) {
            j.onExtended(n.onClassExtended, j);
            delete n.onClassExtended
        }
    }, true);
    c.registerPreprocessor("statics", function (h, i) {
        h.addStatics(i.statics);
        delete i.statics
    });
    c.registerPreprocessor("inheritableStatics", function (h, i) {
        h.addInheritableStatics(i.inheritableStatics);
        delete i.inheritableStatics
    });
    c.registerPreprocessor("config", function (h, k) {
        var j = k.config, i = h.prototype;
        delete k.config;
        Ext.Object.each(j, function (n, w) {
            var u = c.getConfigNameMap(n), q = u.internal, l = u.initialized, v = u.apply, o = u.update, t = u.set, m = u.get, y = (t in i) || k.hasOwnProperty(t), p = (v in i) || k.hasOwnProperty(v), r = (o in i) || k.hasOwnProperty(o), x, s;
            if (w === null || (!y && !p && !r)) {
                i[q] = w;
                i[l] = true
            } else {
                i[l] = false
            }
            if (!y) {
                k[t] = function (B) {
                    var A = this[q], z = this[v], C = this[o];
                    if (!this[l]) {
                        this[l] = true
                    }
                    if (z) {
                        B = z.call(this, B, A)
                    }
                    if (typeof B != "undefined") {
                        this[q] = B;
                        if (C && B !== A) {
                            C.call(this, B, A)
                        }
                    }
                    return this
                }
            }
            if (!(m in i) || k.hasOwnProperty(m)) {
                s = k[m] || false;
                if (s) {
                    x = function () {
                        return s.apply(this, arguments)
                    }
                } else {
                    x = function () {
                        return this[q]
                    }
                }
                k[m] = function () {
                    var z;
                    if (!this[l]) {
                        this[l] = true;
                        this[t](this.config[n])
                    }
                    z = this[m];
                    if ("$previous" in z) {
                        z.$previous = x
                    } else {
                        this[m] = x
                    }
                    return x.apply(this, arguments)
                }
            }
        });
        h.addConfig(j, true)
    });
    c.registerPreprocessor("mixins", function (l, p, h) {
        var j = p.mixins, m, k, n, o;
        delete p.mixins;
        Ext.Function.interceptBefore(h, "onCreated", function () {
            if (j instanceof Array) {
                for (n = 0, o = j.length; n < o; n++) {
                    k = j[n];
                    m = k.prototype.mixinId || k.$className;
                    l.mixin(m, k)
                }
            } else {
                for (var i in j) {
                    if (j.hasOwnProperty(i)) {
                        l.mixin(i, j[i])
                    }
                }
            }
        })
    });
    Ext.extend = function (j, k, i) {
        if (arguments.length === 2 && Ext.isObject(k)) {
            i = k;
            k = j;
            j = null
        }
        var h;
        if (!k) {
            throw new Error("[Ext.extend] Attempting to extend from a class which has not been loaded on the page.")
        }
        i.extend = k;
        i.preprocessors = ["extend", "statics", "inheritableStatics", "mixins", "config"];
        if (j) {
            h = new c(j, i);
            h.prototype.constructor = j
        } else {
            h = new c(i)
        }
        h.prototype.override = function (n) {
            for (var l in n) {
                if (n.hasOwnProperty(l)) {
                    this[l] = n[l]
                }
            }
        };
        return h
    }
}());
(function (c, e, h, d, g) {
    function a() {
        function i() {
            return this.constructor.apply(this, arguments) || null
        }

        return i
    }

    var b = Ext.ClassManager = {classes: {}, existCache: {}, namespaceRewrites: [
        {from: "Ext.", to: Ext}
    ], maps: {alternateToName: {}, aliasToName: {}, nameToAliases: {}, nameToAlternates: {}}, enableNamespaceParseCache: true, namespaceParseCache: {}, instantiators: [], isCreated: function (n) {
        var m = this.existCache, l, o, k, j, p;
        if (this.classes[n] || m[n]) {
            return true
        }
        j = g;
        p = this.parseNamespace(n);
        for (l = 0, o = p.length; l < o; l++) {
            k = p[l];
            if (typeof k != "string") {
                j = k
            } else {
                if (!j || !j[k]) {
                    return false
                }
                j = j[k]
            }
        }
        m[n] = true;
        this.triggerCreated(n);
        return true
    }, createdListeners: [], nameCreatedListeners: {}, triggerCreated: function (s) {
        var u = this.createdListeners, m = this.nameCreatedListeners, n = this.maps.nameToAlternates[s], t = [s], p, r, o, q, l, k;
        for (p = 0, r = u.length; p < r; p++) {
            l = u[p];
            l.fn.call(l.scope, s)
        }
        if (n) {
            t.push.apply(t, n)
        }
        for (p = 0, r = t.length; p < r; p++) {
            k = t[p];
            u = m[k];
            if (u) {
                for (o = 0, q = u.length; o < q; o++) {
                    l = u[o];
                    l.fn.call(l.scope, k)
                }
                delete m[k]
            }
        }
    }, onCreated: function (m, l, k) {
        var j = this.createdListeners, i = this.nameCreatedListeners, n = {fn: m, scope: l};
        if (k) {
            if (this.isCreated(k)) {
                m.call(l, k);
                return
            }
            if (!i[k]) {
                i[k] = []
            }
            i[k].push(n)
        } else {
            j.push(n)
        }
    }, parseNamespace: function (l) {
        var j = this.namespaceParseCache, m, o, q, k, t, s, r, n, p;
        if (this.enableNamespaceParseCache) {
            if (j.hasOwnProperty(l)) {
                return j[l]
            }
        }
        m = [];
        o = this.namespaceRewrites;
        q = g;
        k = l;
        for (n = 0, p = o.length; n < p; n++) {
            t = o[n];
            s = t.from;
            r = t.to;
            if (k === s || k.substring(0, s.length) === s) {
                k = k.substring(s.length);
                if (typeof r != "string") {
                    q = r
                } else {
                    m = m.concat(r.split("."))
                }
                break
            }
        }
        m.push(q);
        m = m.concat(k.split("."));
        if (this.enableNamespaceParseCache) {
            j[l] = m
        }
        return m
    }, setNamespace: function (m, p) {
        var k = g, q = this.parseNamespace(m), o = q.length - 1, j = q[o], n, l;
        for (n = 0; n < o; n++) {
            l = q[n];
            if (typeof l != "string") {
                k = l
            } else {
                if (!k[l]) {
                    k[l] = {}
                }
                k = k[l]
            }
        }
        k[j] = p;
        return k[j]
    }, createNamespaces: function () {
        var k = g, p, m, n, l, o, q;
        for (n = 0, o = arguments.length; n < o; n++) {
            p = this.parseNamespace(arguments[n]);
            for (l = 0, q = p.length; l < q; l++) {
                m = p[l];
                if (typeof m != "string") {
                    k = m
                } else {
                    if (!k[m]) {
                        k[m] = {}
                    }
                    k = k[m]
                }
            }
        }
        return k
    }, set: function (i, m) {
        var l = this, o = l.maps, n = o.nameToAlternates, k = l.getName(m), j;
        l.classes[i] = l.setNamespace(i, m);
        if (k && k !== i) {
            o.alternateToName[i] = k;
            j = n[k] || (n[k] = []);
            j.push(i)
        }
        return this
    }, get: function (l) {
        var n = this.classes, j, p, k, m, o;
        if (n[l]) {
            return n[l]
        }
        j = g;
        p = this.parseNamespace(l);
        for (m = 0, o = p.length; m < o; m++) {
            k = p[m];
            if (typeof k != "string") {
                j = k
            } else {
                if (!j || !j[k]) {
                    return null
                }
                j = j[k]
            }
        }
        return j
    }, setAlias: function (i, j) {
        var l = this.maps.aliasToName, m = this.maps.nameToAliases, k;
        if (typeof i == "string") {
            k = i
        } else {
            k = this.getName(i)
        }
        if (j && l[j] !== k) {
            l[j] = k
        }
        if (!m[k]) {
            m[k] = []
        }
        if (j) {
            Ext.Array.include(m[k], j)
        }
        return this
    }, addNameAliasMappings: function (j) {
        var o = this.maps.aliasToName, p = this.maps.nameToAliases, m, n, l, k;
        for (m in j) {
            n = p[m] || (p[m] = []);
            for (k = 0; k < j[m].length; k++) {
                l = j[m][k];
                if (!o[l]) {
                    o[l] = m;
                    n.push(l)
                }
            }
        }
        return this
    }, addNameAlternateMappings: function (m) {
        var j = this.maps.alternateToName, p = this.maps.nameToAlternates, l, n, o, k;
        for (l in m) {
            n = p[l] || (p[l] = []);
            for (k = 0; k < m[l].length; k++) {
                o = m[l];
                if (!j[o]) {
                    j[o] = l;
                    n.push(o)
                }
            }
        }
        return this
    }, getByAlias: function (i) {
        return this.get(this.getNameByAlias(i))
    }, getNameByAlias: function (i) {
        return this.maps.aliasToName[i] || ""
    }, getNameByAlternate: function (i) {
        return this.maps.alternateToName[i] || ""
    }, getAliasesByName: function (i) {
        return this.maps.nameToAliases[i] || []
    }, getName: function (i) {
        return i && i.$className || ""
    }, getClass: function (i) {
        return i && i.self || null
    }, create: function (j, l, i) {
        var k = a();
        if (typeof l == "function") {
            l = l(k)
        }
        l.$className = j;
        return new c(k, l, function () {
            var m = l.postprocessors || b.defaultPostprocessors, t = b.postprocessors, u = [], s, o, r, n, q, p, v;
            delete l.postprocessors;
            for (o = 0, r = m.length; o < r; o++) {
                s = m[o];
                if (typeof s == "string") {
                    s = t[s];
                    p = s.properties;
                    if (p === true) {
                        u.push(s.fn)
                    } else {
                        if (p) {
                            for (n = 0, q = p.length; n < q; n++) {
                                v = p[n];
                                if (l.hasOwnProperty(v)) {
                                    u.push(s.fn);
                                    break
                                }
                            }
                        }
                    }
                } else {
                    u.push(s)
                }
            }
            l.postprocessors = u;
            l.createdFn = i;
            b.processCreate(j, this, l)
        })
    }, processCreate: function (l, j, n) {
        var m = this, i = n.postprocessors.shift(), k = n.createdFn;
        if (!i) {
            if (l) {
                m.set(l, j)
            }
            if (k) {
                k.call(j, j)
            }
            if (l) {
                m.triggerCreated(l)
            }
            return
        }
        if (i.call(m, l, j, n, m.processCreate) !== false) {
            m.processCreate(l, j, n)
        }
    }, createOverride: function (l, p, j) {
        var o = this, n = p.override, k = p.requires, i = p.uses, m = function () {
            var q, r;
            if (k) {
                r = k;
                k = null;
                Ext.Loader.require(r, m)
            } else {
                q = o.get(n);
                delete p.override;
                delete p.requires;
                delete p.uses;
                Ext.override(q, p);
                o.triggerCreated(l);
                if (i) {
                    Ext.Loader.addUsedClasses(i)
                }
                if (j) {
                    j.call(q)
                }
            }
        };
        o.existCache[l] = true;
        o.onCreated(m, o, n);
        return o
    }, instantiateByAlias: function () {
        var j = arguments[0], i = h.call(arguments), k = this.getNameByAlias(j);
        if (!k) {
            k = this.maps.aliasToName[j];
            Ext.syncRequire(k)
        }
        i[0] = k;
        return this.instantiate.apply(this, i)
    }, instantiate: function () {
        var k = arguments[0], m = typeof k, j = h.call(arguments, 1), l = k, n, i;
        if (m != "function") {
            if (m != "string" && j.length === 0) {
                j = [k];
                k = k.xclass
            }
            i = this.get(k)
        } else {
            i = k
        }
        if (!i) {
            n = this.getNameByAlias(k);
            if (n) {
                k = n;
                i = this.get(k)
            }
        }
        if (!i) {
            n = this.getNameByAlternate(k);
            if (n) {
                k = n;
                i = this.get(k)
            }
        }
        if (!i) {
            Ext.syncRequire(k);
            i = this.get(k)
        }
        return this.getInstantiator(j.length)(i, j)
    }, dynInstantiate: function (j, i) {
        i = d(i, true);
        i.unshift(j);
        return this.instantiate.apply(this, i)
    }, getInstantiator: function (m) {
        var l = this.instantiators, n, k, j;
        n = l[m];
        if (!n) {
            k = m;
            j = [];
            for (k = 0; k < m; k++) {
                j.push("a[" + k + "]")
            }
            n = l[m] = new Function("c", "a", "return new c(" + j.join(",") + ")")
        }
        return n
    }, postprocessors: {}, defaultPostprocessors: [], registerPostprocessor: function (j, m, k, i, l) {
        if (!i) {
            i = "last"
        }
        if (!k) {
            k = [j]
        }
        this.postprocessors[j] = {name: j, properties: k || false, fn: m};
        this.setDefaultPostprocessorPosition(j, i, l);
        return this
    }, setDefaultPostprocessors: function (i) {
        this.defaultPostprocessors = d(i);
        return this
    }, setDefaultPostprocessorPosition: function (j, m, l) {
        var k = this.defaultPostprocessors, i;
        if (typeof m == "string") {
            if (m === "first") {
                k.unshift(j);
                return this
            } else {
                if (m === "last") {
                    k.push(j);
                    return this
                }
            }
            m = (m === "after") ? 1 : -1
        }
        i = Ext.Array.indexOf(k, l);
        if (i !== -1) {
            Ext.Array.splice(k, Math.max(0, i + m), 0, j)
        }
        return this
    }, getNamesByExpression: function (q) {
        var o = this.maps.nameToAliases, r = [], j, n, l, k, s, m, p;
        if (q.indexOf("*") !== -1) {
            q = q.replace(/\*/g, "(.*?)");
            s = new RegExp("^" + q + "$");
            for (j in o) {
                if (o.hasOwnProperty(j)) {
                    l = o[j];
                    if (j.search(s) !== -1) {
                        r.push(j)
                    } else {
                        for (m = 0, p = l.length; m < p; m++) {
                            n = l[m];
                            if (n.search(s) !== -1) {
                                r.push(j);
                                break
                            }
                        }
                    }
                }
            }
        } else {
            k = this.getNameByAlias(q);
            if (k) {
                r.push(k)
            } else {
                k = this.getNameByAlternate(q);
                if (k) {
                    r.push(k)
                } else {
                    r.push(q)
                }
            }
        }
        return r
    }};
    b.registerPostprocessor("alias", function (l, k, o) {
        var j = o.alias, m, n;
        for (m = 0, n = j.length; m < n; m++) {
            e = j[m];
            this.setAlias(k, e)
        }
    }, ["xtype", "alias"]);
    b.registerPostprocessor("singleton", function (j, i, l, k) {
        k.call(this, j, new i(), l);
        return false
    });
    b.registerPostprocessor("alternateClassName", function (k, j, o) {
        var m = o.alternateClassName, l, n, p;
        if (!(m instanceof Array)) {
            m = [m]
        }
        for (l = 0, n = m.length; l < n; l++) {
            p = m[l];
            this.set(p, j)
        }
    });
    Ext.apply(Ext, {create: e(b, "instantiate"), widget: function (k, j) {
        var o = k, l, m, i, n;
        if (typeof o != "string") {
            j = k;
            o = j.xtype
        } else {
            j = j || {}
        }
        if (j.isComponent) {
            return j
        }
        l = "widget." + o;
        m = b.getNameByAlias(l);
        if (!m) {
            n = true
        }
        i = b.get(m);
        if (n || !i) {
            return b.instantiateByAlias(l, j)
        }
        return new i(j)
    }, createByAlias: e(b, "instantiateByAlias"), define: function (j, k, i) {
        if (k.override) {
            return b.createOverride.apply(b, arguments)
        }
        return b.create.apply(b, arguments)
    }, getClassName: e(b, "getName"), getDisplayName: function (i) {
        if (i) {
            if (i.displayName) {
                return i.displayName
            }
            if (i.$name && i.$class) {
                return Ext.getClassName(i.$class) + "#" + i.$name
            }
            if (i.$className) {
                return i.$className
            }
        }
        return"Anonymous"
    }, getClass: e(b, "getClass"), namespace: e(b, "createNamespaces")});
    Ext.createWidget = Ext.widget;
    Ext.ns = Ext.namespace;
    c.registerPreprocessor("className", function (i, j) {
        if (j.$className) {
            i.$className = j.$className
        }
    }, true, "first");
    c.registerPreprocessor("alias", function (u, o) {
        var s = u.prototype, l = d(o.xtype), j = d(o.alias), v = "widget.", t = v.length, p = Array.prototype.slice.call(s.xtypesChain || []), m = Ext.merge({}, s.xtypesMap || {}), n, r, q, k;
        for (n = 0, r = j.length; n < r; n++) {
            q = j[n];
            if (q.substring(0, t) === v) {
                k = q.substring(t);
                Ext.Array.include(l, k)
            }
        }
        u.xtype = o.xtype = l[0];
        o.xtypes = l;
        for (n = 0, r = l.length; n < r; n++) {
            k = l[n];
            if (!m[k]) {
                m[k] = true;
                p.push(k)
            }
        }
        o.xtypesChain = p;
        o.xtypesMap = m;
        Ext.Function.interceptAfter(o, "onClassCreated", function () {
            var i = s.mixins, x, w;
            for (x in i) {
                if (i.hasOwnProperty(x)) {
                    w = i[x];
                    l = w.xtypes;
                    if (l) {
                        for (n = 0, r = l.length; n < r; n++) {
                            k = l[n];
                            if (!m[k]) {
                                m[k] = true;
                                p.push(k)
                            }
                        }
                    }
                }
            }
        });
        for (n = 0, r = l.length; n < r; n++) {
            k = l[n];
            Ext.Array.include(j, v + k)
        }
        o.alias = j
    }, ["xtype", "alias"])
}(Ext.Class, Ext.Function.alias, Array.prototype.slice, Ext.Array.from, Ext.global));
Ext.Loader = new function () {
    var j = this, b = Ext.ClassManager, r = Ext.Class, e = Ext.Function.flexSetter, m = Ext.Function.alias, a = Ext.Function.pass, d = Ext.Function.defer, h = Ext.Array.erase, l = ["extend", "mixins", "requires"], t = {}, k = [], c = /\/\.\//g, g = /\./g;
    Ext.apply(j, {isInHistory: t, history: k, config: {enabled: false, scriptChainDelay: false, disableCaching: true, disableCachingParam: "_dc", garbageCollect: false, paths: {Ext: "."}, preserveScripts: true, scriptCharset: undefined}, setConfig: function (w, x) {
        if (Ext.isObject(w) && arguments.length === 1) {
            Ext.merge(j.config, w)
        } else {
            j.config[w] = (Ext.isObject(x)) ? Ext.merge(j.config[w], x) : x
        }
        return j
    }, getConfig: function (w) {
        if (w) {
            return j.config[w]
        }
        return j.config
    }, setPath: e(function (w, x) {
        j.config.paths[w] = x;
        return j
    }), addClassPathMappings: function (x) {
        var w;
        for (w in x) {
            j.config.paths[w] = x[w]
        }
        return j
    }, getPath: function (w) {
        var y = "", z = j.config.paths, x = j.getPrefix(w);
        if (x.length > 0) {
            if (x === w) {
                return z[x]
            }
            y = z[x];
            w = w.substring(x.length + 1)
        }
        if (y.length > 0) {
            y += "/"
        }
        return y.replace(c, "/") + w.replace(g, "/") + ".js"
    }, getPrefix: function (x) {
        var z = j.config.paths, y, w = "";
        if (z.hasOwnProperty(x)) {
            return x
        }
        for (y in z) {
            if (z.hasOwnProperty(y) && y + "." === x.substring(0, y.length + 1)) {
                if (y.length > w.length) {
                    w = y
                }
            }
        }
        return w
    }, isAClassNameWithAKnownPrefix: function (w) {
        var x = j.getPrefix(w);
        return x !== "" && x !== w
    }, require: function (y, x, w, z) {
        if (x) {
            x.call(w)
        }
    }, syncRequire: function () {
    }, exclude: function (w) {
        return{require: function (z, y, x) {
            return j.require(z, y, x, w)
        }, syncRequire: function (z, y, x) {
            return j.syncRequire(z, y, x, w)
        }}
    }, onReady: function (z, y, A, w) {
        var x;
        if (A !== false && Ext.onDocumentReady) {
            x = z;
            z = function () {
                Ext.onDocumentReady(x, y, w)
            }
        }
        z.call(y)
    }});
    var o = [], p = {}, s = {}, q = {}, n = {}, u = [], v = [], i = {};
    Ext.apply(j, {documentHead: typeof document != "undefined" && (document.head || document.getElementsByTagName("head")[0]), isLoading: false, queue: o, isClassFileLoaded: p, isFileLoaded: s, readyListeners: u, optionalRequires: v, requiresMap: i, numPendingFiles: 0, numLoadedFiles: 0, hasFileLoadError: false, classNameToFilePathMap: q, scriptsLoading: 0, syncModeEnabled: false, scriptElements: n, refreshQueue: function () {
        var A = o.length, x, z, w, y;
        if (!A && !j.scriptsLoading) {
            return j.triggerReady()
        }
        for (x = 0; x < A; x++) {
            z = o[x];
            if (z) {
                y = z.requires;
                if (y.length > j.numLoadedFiles) {
                    continue
                }
                for (w = 0; w < y.length;) {
                    if (b.isCreated(y[w])) {
                        h(y, w, 1)
                    } else {
                        w++
                    }
                }
                if (z.requires.length === 0) {
                    h(o, x, 1);
                    z.callback.call(z.scope);
                    j.refreshQueue();
                    break
                }
            }
        }
        return j
    }, injectScriptElement: function (w, D, A, F, y) {
        var E = document.createElement("script"), B = false, x = j.config, C = function () {
            if (!B) {
                B = true;
                E.onload = E.onreadystatechange = E.onerror = null;
                if (typeof x.scriptChainDelay == "number") {
                    d(D, x.scriptChainDelay, F)
                } else {
                    D.call(F)
                }
                j.cleanupScriptElement(E, x.preserveScripts === false, x.garbageCollect)
            }
        }, z = function (G) {
            d(A, 1, F);
            j.cleanupScriptElement(E, x.preserveScripts === false, x.garbageCollect)
        };
        E.type = "text/javascript";
        E.onerror = z;
        y = y || x.scriptCharset;
        if (y) {
            E.charset = y
        }
        if ("addEventListener" in E) {
            E.onload = C
        } else {
            if ("readyState" in E) {
                E.onreadystatechange = function () {
                    if (this.readyState == "loaded" || this.readyState == "complete") {
                        C()
                    }
                }
            } else {
                E.onload = C
            }
        }
        E.src = w;
        (j.documentHead || document.getElementsByTagName("head")[0]).appendChild(E);
        return E
    }, removeScriptElement: function (w) {
        if (n[w]) {
            j.cleanupScriptElement(n[w], true, !!j.getConfig("garbageCollect"));
            delete n[w]
        }
        return j
    }, cleanupScriptElement: function (y, x, z) {
        var A;
        y.onload = y.onreadystatechange = y.onerror = null;
        if (x) {
            Ext.removeNode(y);
            if (z) {
                for (A in y) {
                    try {
                        y[A] = null;
                        delete y[A]
                    } catch (w) {
                    }
                }
            }
        }
        return j
    }, loadScript: function (F) {
        var z = j.getConfig(), y = typeof F == "string", x = y ? F : F.url, B = !y && F.onError, C = !y && F.onLoad, E = !y && F.scope, D = function () {
            j.numPendingFiles--;
            j.scriptsLoading--;
            if (B) {
                B.call(E, "Failed loading '" + x + "', please verify that the file exists")
            }
            if (j.numPendingFiles + j.scriptsLoading === 0) {
                j.refreshQueue()
            }
        }, A = function () {
            j.numPendingFiles--;
            j.scriptsLoading--;
            if (C) {
                C.call(E)
            }
            if (j.numPendingFiles + j.scriptsLoading === 0) {
                j.refreshQueue()
            }
        }, w;
        j.isLoading = true;
        j.numPendingFiles++;
        j.scriptsLoading++;
        w = z.disableCaching ? (x + "?" + z.disableCachingParam + "=" + Ext.Date.now()) : x;
        n[x] = j.injectScriptElement(w, A, D)
    }, loadScriptFile: function (x, E, C, H, w) {
        if (s[x]) {
            return j
        }
        var z = j.getConfig(), I = x + (z.disableCaching ? ("?" + z.disableCachingParam + "=" + Ext.Date.now()) : ""), y = false, G, A, F, B = "";
        H = H || j;
        j.isLoading = true;
        if (!w) {
            F = function () {
            };
            n[x] = j.injectScriptElement(I, E, F, H)
        } else {
            if (typeof XMLHttpRequest != "undefined") {
                G = new XMLHttpRequest()
            } else {
                G = new ActiveXObject("Microsoft.XMLHTTP")
            }
            try {
                G.open("GET", I, false);
                G.send(null)
            } catch (D) {
                y = true
            }
            A = (G.status === 1223) ? 204 : (G.status === 0 && (self.location || {}).protocol == "file:") ? 200 : G.status;
            y = y || (A === 0);
            if (y) {
            } else {
                if ((A >= 200 && A < 300) || (A === 304)) {
                    if (!Ext.isIE) {
                        B = "\n//@ sourceURL=" + x
                    }
                    Ext.globalEval(G.responseText + B);
                    E.call(H)
                } else {
                }
            }
            G = null
        }
    }, syncRequire: function () {
        var w = j.syncModeEnabled;
        if (!w) {
            j.syncModeEnabled = true
        }
        j.require.apply(j, arguments);
        if (!w) {
            j.syncModeEnabled = false
        }
        j.refreshQueue()
    }, require: function (O, F, z, B) {
        var H = {}, y = {}, E = [], Q = [], N = [], x = [], D, P, J, I, w, C, M, L, K, G, A;
        if (B) {
            B = (typeof B === "string") ? [B] : B;
            for (L = 0, G = B.length; L < G; L++) {
                w = B[L];
                if (typeof w == "string" && w.length > 0) {
                    E = b.getNamesByExpression(w);
                    for (K = 0, A = E.length; K < A; K++) {
                        H[E[K]] = true
                    }
                }
            }
        }
        O = (typeof O === "string") ? [O] : (O ? O : []);
        if (F) {
            if (F.length > 0) {
                D = function () {
                    var S = [], R, T;
                    for (R = 0, T = x.length; R < T; R++) {
                        S.push(b.get(x[R]))
                    }
                    return F.apply(this, S)
                }
            } else {
                D = F
            }
        } else {
            D = Ext.emptyFn
        }
        z = z || Ext.global;
        for (L = 0, G = O.length; L < G; L++) {
            I = O[L];
            if (typeof I == "string" && I.length > 0) {
                Q = b.getNamesByExpression(I);
                A = Q.length;
                for (K = 0; K < A; K++) {
                    M = Q[K];
                    if (H[M] !== true) {
                        x.push(M);
                        if (!b.isCreated(M) && !y[M]) {
                            y[M] = true;
                            N.push(M)
                        }
                    }
                }
            }
        }
        if (N.length > 0) {
            if (!j.config.enabled) {
                throw new Error("Ext.Loader is not enabled, so dependencies cannot be resolved dynamically. Missing required class" + ((N.length > 1) ? "es" : "") + ": " + N.join(", "))
            }
        } else {
            D.call(z);
            return j
        }
        P = j.syncModeEnabled;
        if (!P) {
            o.push({requires: N.slice(), callback: D, scope: z})
        }
        G = N.length;
        for (L = 0; L < G; L++) {
            C = N[L];
            J = j.getPath(C);
            if (P && p.hasOwnProperty(C)) {
                j.numPendingFiles--;
                j.removeScriptElement(J);
                delete p[C]
            }
            if (!p.hasOwnProperty(C)) {
                p[C] = false;
                q[C] = J;
                j.numPendingFiles++;
                j.loadScriptFile(J, a(j.onFileLoaded, [C, J], j), a(j.onFileLoadError, [C, J], j), j, P)
            }
        }
        if (P) {
            D.call(z);
            if (G === 1) {
                return b.get(C)
            }
        }
        return j
    }, onFileLoaded: function (x, w) {
        j.numLoadedFiles++;
        p[x] = true;
        s[w] = true;
        j.numPendingFiles--;
        if (j.numPendingFiles === 0) {
            j.refreshQueue()
        }
    }, onFileLoadError: function (y, x, w, z) {
        j.numPendingFiles--;
        j.hasFileLoadError = true
    }, addUsedClasses: function (y) {
        var w, x, z;
        if (y) {
            y = (typeof y == "string") ? [y] : y;
            for (x = 0, z = y.length; x < z; x++) {
                w = y[x];
                if (typeof w == "string" && !Ext.Array.contains(v, w)) {
                    v.push(w)
                }
            }
        }
        return j
    }, triggerReady: function () {
        var x, w, y = v;
        if (j.isLoading) {
            j.isLoading = false;
            if (y.length !== 0) {
                y = y.slice();
                v.length = 0;
                j.require(y, j.triggerReady, j);
                return j
            }
        }
        while (u.length && !j.isLoading) {
            x = u.shift();
            x.fn.call(x.scope)
        }
        return j
    }, onReady: function (z, y, A, w) {
        var x;
        if (A !== false && Ext.onDocumentReady) {
            x = z;
            z = function () {
                Ext.onDocumentReady(x, y, w)
            }
        }
        if (!j.isLoading) {
            z.call(y)
        } else {
            u.push({fn: z, scope: y})
        }
    }, historyPush: function (w) {
        if (w && p.hasOwnProperty(w) && !t[w]) {
            t[w] = true;
            k.push(w)
        }
        return j
    }});
    Ext.disableCacheBuster = function (x, y) {
        var w = new Date();
        w.setTime(w.getTime() + (x ? 10 * 365 : -1) * 24 * 60 * 60 * 1000);
        w = w.toGMTString();
        document.cookie = "ext-cache=1; expires=" + w + "; path=" + (y || "/")
    };
    Ext.require = m(j, "require");
    Ext.syncRequire = m(j, "syncRequire");
    Ext.exclude = m(j, "exclude");
    Ext.onReady = function (y, x, w) {
        j.onReady(y, x, true, w)
    };
    r.registerPreprocessor("loader", function (M, A, L, K) {
        var H = this, F = [], w, G = b.getName(M), z, y, E, D, J, C, x, I, B;
        for (z = 0, E = l.length; z < E; z++) {
            C = l[z];
            if (A.hasOwnProperty(C)) {
                x = A[C];
                if (typeof x == "string") {
                    F.push(x)
                } else {
                    if (x instanceof Array) {
                        for (y = 0, D = x.length; y < D; y++) {
                            J = x[y];
                            if (typeof J == "string") {
                                F.push(J)
                            }
                        }
                    } else {
                        if (typeof x != "function") {
                            for (y in x) {
                                if (x.hasOwnProperty(y)) {
                                    J = x[y];
                                    if (typeof J == "string") {
                                        F.push(J)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (F.length === 0) {
            return
        }
        j.require(F, function () {
            for (z = 0, E = l.length; z < E; z++) {
                C = l[z];
                if (A.hasOwnProperty(C)) {
                    x = A[C];
                    if (typeof x == "string") {
                        A[C] = b.get(x)
                    } else {
                        if (x instanceof Array) {
                            for (y = 0, D = x.length; y < D; y++) {
                                J = x[y];
                                if (typeof J == "string") {
                                    A[C][y] = b.get(J)
                                }
                            }
                        } else {
                            if (typeof x != "function") {
                                for (var N in x) {
                                    if (x.hasOwnProperty(N)) {
                                        J = x[N];
                                        if (typeof J == "string") {
                                            A[C][N] = b.get(J)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            K.call(H, M, A, L)
        });
        return false
    }, true, "after", "className");
    b.registerPostprocessor("uses", function (y, x, z) {
        var w = z.uses;
        if (w) {
            j.addUsedClasses(w)
        }
    });
    b.onCreated(j.historyPush)
};
if (Ext._classPathMetadata) {
    Ext.Loader.addClassPathMappings(Ext._classPathMetadata);
    Ext._classPathMetadata = null
}
(function () {
    var a = document.getElementsByTagName("script"), b = a[a.length - 1], d = b.src, c = d.substring(0, d.lastIndexOf("/") + 1), e = Ext.Loader;
    e.setConfig({enabled: true, disableCaching: true, paths: {Ext: c + "src"}})
})();
Ext._endTime = new Date().getTime();
if (Ext._beforereadyhandler) {
    Ext._beforereadyhandler()
}
Ext.Error = Ext.extend(Error, {statics: {ignore: false, raise: function (a) {
    a = a || {};
    if (Ext.isString(a)) {
        a = {msg: a}
    }
    var c = this.raise.caller, b;
    if (c) {
        if (c.$name) {
            a.sourceMethod = c.$name
        }
        if (c.$owner) {
            a.sourceClass = c.$owner.$className
        }
    }
    if (Ext.Error.handle(a) !== true) {
        b = Ext.Error.prototype.toString.call(a);
        Ext.log({msg: b, level: "error", dump: a, stack: true});
        throw new Ext.Error(a)
    }
}, handle: function () {
    return Ext.Error.ignore
}}, name: "Ext.Error", constructor: function (a) {
    if (Ext.isString(a)) {
        a = {msg: a}
    }
    var b = this;
    Ext.apply(b, a);
    b.message = b.message || b.msg
}, toString: function () {
    var c = this, b = c.sourceClass ? c.sourceClass : "", a = c.sourceMethod ? "." + c.sourceMethod + "(): " : "", d = c.msg || "(No description provided)";
    return b + a + d
}});
Ext.deprecated = function (a) {
    return Ext.emptyFn
};
Ext.JSON = (new (function () {
    var me = this, encodingFunction, decodingFunction, useNative = null, useHasOwn = !!{}.hasOwnProperty, isNative = function () {
        if (useNative === null) {
            useNative = Ext.USE_NATIVE_JSON && window.JSON && JSON.toString() == "[object JSON]"
        }
        return useNative
    }, pad = function (n) {
        return n < 10 ? "0" + n : n
    }, doDecode = function (json) {
        return eval("(" + json + ")")
    }, doEncode = function (o, newline) {
        if (o === null || o === undefined) {
            return"null"
        } else {
            if (Ext.isDate(o)) {
                return Ext.JSON.encodeDate(o)
            } else {
                if (Ext.isString(o)) {
                    return Ext.JSON.encodeString(o)
                } else {
                    if (typeof o == "number") {
                        return isFinite(o) ? String(o) : "null"
                    } else {
                        if (Ext.isBoolean(o)) {
                            return String(o)
                        } else {
                            if (o.toJSON) {
                                return o.toJSON()
                            } else {
                                if (Ext.isArray(o)) {
                                    return encodeArray(o, newline)
                                } else {
                                    if (Ext.isObject(o)) {
                                        return encodeObject(o, newline)
                                    } else {
                                        if (typeof o === "function") {
                                            return"null"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return"undefined"
    }, m = {"\b": "\\b", "\t": "\\t", "\n": "\\n", "\f": "\\f", "\r": "\\r", '"': '\\"', "\\": "\\\\", "\x0b": "\\u000b"}, charToReplace = /[\\\"\x00-\x1f\x7f-\uffff]/g, encodeString = function (s) {
        return'"' + s.replace(charToReplace, function (a) {
            var c = m[a];
            return typeof c === "string" ? c : "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
        }) + '"'
    }, encodeArray = function (o, newline) {
        var a = ["[", ""], len = o.length, i;
        for (i = 0; i < len; i += 1) {
            a.push(Ext.JSON.encodeValue(o[i]), ",")
        }
        a[a.length - 1] = "]";
        return a.join("")
    }, encodeObject = function (o, newline) {
        var a = ["{", ""], i;
        for (i in o) {
            if (!useHasOwn || o.hasOwnProperty(i)) {
                a.push(Ext.JSON.encodeValue(i), ":", Ext.JSON.encodeValue(o[i]), ",")
            }
        }
        a[a.length - 1] = "}";
        return a.join("")
    };
    me.encodeString = encodeString;
    me.encodeValue = doEncode;
    me.encodeDate = function (o) {
        return'"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-" + pad(o.getDate()) + "T" + pad(o.getHours()) + ":" + pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"'
    };
    me.encode = function (o) {
        if (!encodingFunction) {
            encodingFunction = isNative() ? JSON.stringify : me.encodeValue
        }
        return encodingFunction(o)
    };
    me.decode = function (json, safe) {
        if (!decodingFunction) {
            decodingFunction = isNative() ? JSON.parse : doDecode
        }
        try {
            return decodingFunction(json)
        } catch (e) {
            if (safe === true) {
                return null
            }
            Ext.Error.raise({sourceClass: "Ext.JSON", sourceMethod: "decode", msg: "You're trying to decode an invalid JSON String: " + json})
        }
    }
})());
Ext.encode = Ext.JSON.encode;
Ext.decode = Ext.JSON.decode;
Ext.apply(Ext, {userAgent: navigator.userAgent.toLowerCase(), cache: {}, idSeed: 1000, windowId: "ext-window", documentId: "ext-document", isReady: false, enableGarbageCollector: true, enableListenerCollection: true, addCacheEntry: function (e, b, d) {
    d = d || b.dom;
    var a = e || (b && b.id) || d.id, c = Ext.cache[a] || (Ext.cache[a] = {data: {}, events: {}, dom: d, skipGarbageCollection: !!(d.getElementById || d.navigator)});
    if (b) {
        b.$cache = c;
        c.el = b
    }
    return c
}, updateCacheEntry: function (a, b) {
    a.dom = b;
    if (a.el) {
        a.el.dom = b
    }
    return a
}, id: function (a, c) {
    var b = this, d = "";
    a = Ext.getDom(a, true) || {};
    if (a === document) {
        a.id = b.documentId
    } else {
        if (a === window) {
            a.id = b.windowId
        }
    }
    if (!a.id) {
        if (b.isSandboxed) {
            d = Ext.sandboxName.toLowerCase() + "-"
        }
        a.id = d + (c || "ext-gen") + (++Ext.idSeed)
    }
    return a.id
}, escapeId: (function () {
    var c = /^[a-zA-Z_][a-zA-Z0-9_\-]*$/i, d = /([\W]{1})/g, b = /^(\d)/g, a = function (h, g) {
        return"\\" + g
    }, e = function (h, g) {
        return"\\00" + g.charCodeAt(0).toString(16) + " "
    };
    return function (g) {
        return c.test(g) ? g : g.replace(d, a).replace(b, e)
    }
}()), getBody: (function () {
    var a;
    return function () {
        return a || (a = Ext.get(document.body))
    }
}()), getHead: (function () {
    var a;
    return function () {
        return a || (a = Ext.get(document.getElementsByTagName("head")[0]))
    }
}()), getDoc: (function () {
    var a;
    return function () {
        return a || (a = Ext.get(document))
    }
}()), getCmp: function (a) {
    return Ext.ComponentManager.get(a)
}, getOrientation: function () {
    return window.innerHeight > window.innerWidth ? "portrait" : "landscape"
}, destroy: function () {
    var c = arguments.length, b, a;
    for (b = 0; b < c; b++) {
        a = arguments[b];
        if (a) {
            if (Ext.isArray(a)) {
                this.destroy.apply(this, a)
            } else {
                if (Ext.isFunction(a.destroy)) {
                    a.destroy()
                } else {
                    if (a.dom) {
                        a.remove()
                    }
                }
            }
        }
    }
}, callback: function (d, c, b, a) {
    if (Ext.isFunction(d)) {
        b = b || [];
        c = c || window;
        if (a) {
            Ext.defer(d, a, c, b)
        } else {
            d.apply(c, b)
        }
    }
}, htmlEncode: function (a) {
    return Ext.String.htmlEncode(a)
}, htmlDecode: function (a) {
    return Ext.String.htmlDecode(a)
}, urlAppend: function (a, b) {
    return Ext.String.urlAppend(a, b)
}});
Ext.ns = Ext.namespace;
window.undefined = window.undefined;
(function () {
    var o = function (e) {
        return e.test(Ext.userAgent)
    }, t = document.compatMode == "CSS1Compat", F = function (R, Q) {
        var e;
        return(R && (e = Q.exec(Ext.userAgent))) ? parseFloat(e[1]) : 0
    }, p = document.documentMode, a = o(/opera/), v = a && o(/version\/10\.5/), K = o(/\bchrome\b/), z = o(/webkit/), c = !K && o(/safari/), I = c && o(/applewebkit\/4/), G = c && o(/version\/3/), D = c && o(/version\/4/), j = c && o(/version\/5\.0/), C = c && o(/version\/5/), i = !a && o(/msie/), J = i && ((o(/msie 7/) && p != 8 && p != 9) || p == 7), H = i && ((o(/msie 8/) && p != 7 && p != 9) || p == 8), E = i && ((o(/msie 9/) && p != 7 && p != 8) || p == 9), M = i && o(/msie 6/), b = !z && o(/gecko/), P = b && o(/rv:1\.9/), O = b && o(/rv:2\.0/), N = b && o(/rv:5\./), r = b && o(/rv:10\./), y = P && o(/rv:1\.9\.0/), w = P && o(/rv:1\.9\.1/), u = P && o(/rv:1\.9\.2/), g = o(/windows|win32/), B = o(/macintosh|mac os x/), x = o(/linux/), l = null, m = F(true, /\bchrome\/(\d+\.\d+)/), h = F(true, /\bfirefox\/(\d+\.\d+)/), n = F(i, /msie (\d+\.\d+)/), s = F(a, /version\/(\d+\.\d+)/), d = F(c, /version\/(\d+\.\d+)/), A = F(z, /webkit\/(\d+\.\d+)/), q = /^https/i.test(window.location.protocol), k;
    try {
        document.execCommand("BackgroundImageCache", false, true)
    } catch (L) {
    }
    k = function () {
    };
    k.info = k.warn = k.error = Ext.emptyFn;
    Ext.setVersion("extjs", "4.1.1.1");
    Ext.apply(Ext, {SSL_SECURE_URL: q && i ? "javascript:''" : "about:blank", scopeResetCSS: Ext.buildSettings.scopeResetCSS, resetCls: Ext.buildSettings.baseCSSPrefix + "reset", enableNestedListenerRemoval: false, USE_NATIVE_JSON: false, getDom: function (R, Q) {
        if (!R || !document) {
            return null
        }
        if (R.dom) {
            return R.dom
        } else {
            if (typeof R == "string") {
                var S = Ext.getElementById(R);
                if (S && i && Q) {
                    if (R == S.getAttribute("id")) {
                        return S
                    } else {
                        return null
                    }
                }
                return S
            } else {
                return R
            }
        }
    }, removeNode: M || J || H ? (function () {
        var e;
        return function (S) {
            if (S && S.tagName.toUpperCase() != "BODY") {
                (Ext.enableNestedListenerRemoval) ? Ext.EventManager.purgeElement(S) : Ext.EventManager.removeAll(S);
                var Q = Ext.cache, R = S.id;
                if (Q[R]) {
                    delete Q[R].dom;
                    delete Q[R]
                }
                if (H && S.parentNode) {
                    S.parentNode.removeChild(S)
                }
                e = e || document.createElement("div");
                e.appendChild(S);
                e.innerHTML = ""
            }
        }
    }()) : function (R) {
        if (R && R.parentNode && R.tagName.toUpperCase() != "BODY") {
            (Ext.enableNestedListenerRemoval) ? Ext.EventManager.purgeElement(R) : Ext.EventManager.removeAll(R);
            var e = Ext.cache, Q = R.id;
            if (e[Q]) {
                delete e[Q].dom;
                delete e[Q]
            }
            R.parentNode.removeChild(R)
        }
    }, isStrict: t, isIEQuirks: i && !t, isOpera: a, isOpera10_5: v, isWebKit: z, isChrome: K, isSafari: c, isSafari3: G, isSafari4: D, isSafari5: C, isSafari5_0: j, isSafari2: I, isIE: i, isIE6: M, isIE7: J, isIE8: H, isIE9: E, isGecko: b, isGecko3: P, isGecko4: O, isGecko5: N, isGecko10: r, isFF3_0: y, isFF3_5: w, isFF3_6: u, isFF4: 4 <= h && h < 5, isFF5: 5 <= h && h < 6, isFF10: 10 <= h && h < 11, isLinux: x, isWindows: g, isMac: B, chromeVersion: m, firefoxVersion: h, ieVersion: n, operaVersion: s, safariVersion: d, webKitVersion: A, isSecure: q, BLANK_IMAGE_URL: (M || J) ? "//www.sencha.com/s.gif" : "data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==", value: function (R, e, Q) {
        return Ext.isEmpty(R, Q) ? e : R
    }, escapeRe: function (e) {
        return e.replace(/([-.*+?\^${}()|\[\]\/\\])/g, "\\$1")
    }, addBehaviors: function (T) {
        if (!Ext.isReady) {
            Ext.onReady(function () {
                Ext.addBehaviors(T)
            })
        } else {
            var Q = {}, S, e, R;
            for (e in T) {
                if ((S = e.split("@"))[1]) {
                    R = S[0];
                    if (!Q[R]) {
                        Q[R] = Ext.select(R)
                    }
                    Q[R].on(S[1], T[e])
                }
            }
            Q = null
        }
    }, getScrollbarSize: function (Q) {
        if (!Ext.isReady) {
            return{}
        }
        if (Q || !l) {
            var e = document.body, R = document.createElement("div");
            R.style.width = R.style.height = "100px";
            R.style.overflow = "scroll";
            R.style.position = "absolute";
            e.appendChild(R);
            l = {width: R.offsetWidth - R.clientWidth, height: R.offsetHeight - R.clientHeight};
            e.removeChild(R)
        }
        return l
    }, getScrollBarWidth: function (Q) {
        var e = Ext.getScrollbarSize(Q);
        return e.width + 2
    }, copyTo: function (Q, S, U, T) {
        if (typeof U == "string") {
            U = U.split(/[,;\s]/)
        }
        var V, R = U.length, e;
        for (V = 0; V < R; V++) {
            e = U[V];
            if (T || S.hasOwnProperty(e)) {
                Q[e] = S[e]
            }
        }
        return Q
    }, destroyMembers: function (S) {
        for (var R = 1, Q = arguments, e = Q.length; R < e; R++) {
            Ext.destroy(S[Q[R]]);
            delete S[Q[R]]
        }
    }, log: k, partition: function (e, T) {
        var U = [
            [],
            []
        ], Q, S, R = e.length;
        for (Q = 0; Q < R; Q++) {
            S = e[Q];
            U[(T && T(S, Q, e)) || (!T && S) ? 0 : 1].push(S)
        }
        return U
    }, invoke: function (e, T) {
        var V = [], U = Array.prototype.slice.call(arguments, 2), Q, S, R = e.length;
        for (Q = 0; Q < R; Q++) {
            S = e[Q];
            if (S && typeof S[T] == "function") {
                V.push(S[T].apply(S, U))
            } else {
                V.push(undefined)
            }
        }
        return V
    }, zip: function () {
        var W = Ext.partition(arguments, function (X) {
            return typeof X != "function"
        }), T = W[0], V = W[1][0], e = Ext.max(Ext.pluck(T, "length")), S = [], U, R, Q;
        for (U = 0; U < e; U++) {
            S[U] = [];
            if (V) {
                S[U] = V.apply(V, Ext.pluck(T, U))
            } else {
                for (R = 0, Q = T.length; R < Q; R++) {
                    S[U].push(T[R][U])
                }
            }
        }
        return S
    }, toSentence: function (Q, e) {
        var T = Q.length, S, R;
        if (T <= 1) {
            return Q[0]
        } else {
            S = Q.slice(0, T - 1);
            R = Q[T - 1];
            return Ext.util.Format.format("{0} {1} {2}", S.join(", "), e || "and", R)
        }
    }, useShims: M})
}());
Ext.application = function (a) {
    Ext.require("Ext.app.Application");
    Ext.onReady(function () {
        new Ext.app.Application(a)
    })
};
(function () {
    Ext.ns("Ext.util");
    Ext.util.Format = {};
    var g = Ext.util.Format, e = /<\/?[^>]+>/gi, c = /(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig, b = /\r?\n/g, d = /[^\d\.]/g, a;
    Ext.apply(g, {thousandSeparator: ",", decimalSeparator: ".", currencyPrecision: 2, currencySign: "$", currencyAtEnd: false, undef: function (h) {
        return h !== undefined ? h : ""
    }, defaultValue: function (i, h) {
        return i !== undefined && i !== "" ? i : h
    }, substr: "ab".substr(-1) != "b" ? function (i, k, h) {
        var j = String(i);
        return(k < 0) ? j.substr(Math.max(j.length + k, 0), h) : j.substr(k, h)
    } : function (i, j, h) {
        return String(i).substr(j, h)
    }, lowercase: function (h) {
        return String(h).toLowerCase()
    }, uppercase: function (h) {
        return String(h).toUpperCase()
    }, usMoney: function (h) {
        return g.currency(h, "$", 2)
    }, currency: function (k, m, j, h) {
        var o = "", n = ",0", l = 0;
        k = k - 0;
        if (k < 0) {
            k = -k;
            o = "-"
        }
        j = Ext.isDefined(j) ? j : g.currencyPrecision;
        n += n + (j > 0 ? "." : "");
        for (; l < j; l++) {
            n += "0"
        }
        k = g.number(k, n);
        if ((h || g.currencyAtEnd) === true) {
            return Ext.String.format("{0}{1}{2}", o, k, m || g.currencySign)
        } else {
            return Ext.String.format("{0}{1}{2}", o, m || g.currencySign, k)
        }
    }, date: function (h, i) {
        if (!h) {
            return""
        }
        if (!Ext.isDate(h)) {
            h = new Date(Date.parse(h))
        }
        return Ext.Date.dateFormat(h, i || Ext.Date.defaultFormat)
    }, dateRenderer: function (h) {
        return function (i) {
            return g.date(i, h)
        }
    }, stripTags: function (h) {
        return !h ? h : String(h).replace(e, "")
    }, stripScripts: function (h) {
        return !h ? h : String(h).replace(c, "")
    }, fileSize: function (h) {
        if (h < 1024) {
            return h + " bytes"
        } else {
            if (h < 1048576) {
                return(Math.round(((h * 10) / 1024)) / 10) + " KB"
            } else {
                return(Math.round(((h * 10) / 1048576)) / 10) + " MB"
            }
        }
    }, math: (function () {
        var h = {};
        return function (j, i) {
            if (!h[i]) {
                h[i] = Ext.functionFactory("v", "return v " + i + ";")
            }
            return h[i](j)
        }
    }()), round: function (j, i) {
        var h = Number(j);
        if (typeof i == "number") {
            i = Math.pow(10, i);
            h = Math.round(j * i) / i
        }
        return h
    }, number: function (y, s) {
        if (!s) {
            return y
        }
        y = Ext.Number.from(y, NaN);
        if (isNaN(y)) {
            return""
        }
        var A = g.thousandSeparator, q = g.decimalSeparator, z = false, r = y < 0, k, h, x, w, p, t, o, l, u;
        y = Math.abs(y);
        if (s.substr(s.length - 2) == "/i") {
            if (!a) {
                a = new RegExp("[^\\d\\" + g.decimalSeparator + "]", "g")
            }
            s = s.substr(0, s.length - 2);
            z = true;
            k = s.indexOf(A) != -1;
            h = s.replace(a, "").split(q)
        } else {
            k = s.indexOf(",") != -1;
            h = s.replace(d, "").split(".")
        }
        if (h.length > 2) {
        } else {
            if (h.length > 1) {
                y = Ext.Number.toFixed(y, h[1].length)
            } else {
                y = Ext.Number.toFixed(y, 0)
            }
        }
        x = y.toString();
        h = x.split(".");
        if (k) {
            w = h[0];
            p = [];
            t = w.length;
            o = Math.floor(t / 3);
            l = w.length % 3 || 3;
            for (u = 0; u < t; u += l) {
                if (u !== 0) {
                    l = 3
                }
                p[p.length] = w.substr(u, l);
                o -= 1
            }
            x = p.join(A);
            if (h[1]) {
                x += q + h[1]
            }
        } else {
            if (h[1]) {
                x = h[0] + q + h[1]
            }
        }
        if (r) {
            r = x.replace(/[^1-9]/g, "") !== ""
        }
        return(r ? "-" : "") + s.replace(/[\d,?\.?]+/, x)
    }, numberRenderer: function (h) {
        return function (i) {
            return g.number(i, h)
        }
    }, plural: function (h, i, j) {
        return h + " " + (h == 1 ? i : (j ? j : i + "s"))
    }, nl2br: function (h) {
        return Ext.isEmpty(h) ? "" : h.replace(b, "<br/>")
    }, capitalize: Ext.String.capitalize, ellipsis: Ext.String.ellipsis, format: Ext.String.format, htmlDecode: Ext.String.htmlDecode, htmlEncode: Ext.String.htmlEncode, leftPad: Ext.String.leftPad, trim: Ext.String.trim, parseBox: function (i) {
        i = Ext.isEmpty(i) ? "" : i;
        if (Ext.isNumber(i)) {
            i = i.toString()
        }
        var j = i.split(" "), h = j.length;
        if (h == 1) {
            j[1] = j[2] = j[3] = j[0]
        } else {
            if (h == 2) {
                j[2] = j[0];
                j[3] = j[1]
            } else {
                if (h == 3) {
                    j[3] = j[1]
                }
            }
        }
        return{top: parseInt(j[0], 10) || 0, right: parseInt(j[1], 10) || 0, bottom: parseInt(j[2], 10) || 0, left: parseInt(j[3], 10) || 0}
    }, escapeRegex: function (h) {
        return h.replace(/([\-.*+?\^${}()|\[\]\/\\])/g, "\\$1")
    }})
}());
Ext.define("Ext.util.TaskRunner", {interval: 10, timerId: null, constructor: function (a) {
    var b = this;
    if (typeof a == "number") {
        b.interval = a
    } else {
        if (a) {
            Ext.apply(b, a)
        }
    }
    b.tasks = [];
    b.timerFn = Ext.Function.bind(b.onTick, b)
}, newTask: function (b) {
    var a = new Ext.util.TaskRunner.Task(b);
    a.manager = this;
    return a
}, start: function (a) {
    var c = this, b = new Date().getTime();
    if (!a.pending) {
        c.tasks.push(a);
        a.pending = true
    }
    a.stopped = false;
    a.taskStartTime = b;
    a.taskRunTime = a.fireOnStart !== false ? 0 : a.taskStartTime;
    a.taskRunCount = 0;
    if (!c.firing) {
        if (a.fireOnStart !== false) {
            c.startTimer(0, b)
        } else {
            c.startTimer(a.interval, b)
        }
    }
    return a
}, stop: function (a) {
    if (!a.stopped) {
        a.stopped = true;
        if (a.onStop) {
            a.onStop.call(a.scope || a, a)
        }
    }
    return a
}, stopAll: function () {
    Ext.each(this.tasks, this.stop, this)
}, firing: false, nextExpires: 1e+99, onTick: function () {
    var m = this, e = m.tasks, a = new Date().getTime(), n = 1e+99, k = e.length, c, o, h, b, d, g;
    m.timerId = null;
    m.firing = true;
    for (h = 0; h < k || h < (k = e.length); ++h) {
        b = e[h];
        if (!(g = b.stopped)) {
            c = b.taskRunTime + b.interval;
            if (c <= a) {
                d = 1;
                try {
                    d = b.run.apply(b.scope || b, b.args || [++b.taskRunCount])
                } catch (j) {
                    try {
                        if (b.onError) {
                            d = b.onError.call(b.scope || b, b, j)
                        }
                    } catch (l) {
                    }
                }
                b.taskRunTime = a;
                if (d === false || b.taskRunCount === b.repeat) {
                    m.stop(b);
                    g = true
                } else {
                    g = b.stopped;
                    c = a + b.interval
                }
            }
            if (!g && b.duration && b.duration <= (a - b.taskStartTime)) {
                m.stop(b);
                g = true
            }
        }
        if (g) {
            b.pending = false;
            if (!o) {
                o = e.slice(0, h)
            }
        } else {
            if (o) {
                o.push(b)
            }
            if (n > c) {
                n = c
            }
        }
    }
    if (o) {
        m.tasks = o
    }
    m.firing = false;
    if (m.tasks.length) {
        m.startTimer(n - a, new Date().getTime())
    }
}, startTimer: function (e, c) {
    var d = this, b = c + e, a = d.timerId;
    if (a && d.nextExpires - b > d.interval) {
        clearTimeout(a);
        a = null
    }
    if (!a) {
        if (e < d.interval) {
            e = d.interval
        }
        d.timerId = setTimeout(d.timerFn, e);
        d.nextExpires = b
    }
}}, function () {
    var b = this, a = b.prototype;
    a.destroy = a.stopAll;
    Ext.util.TaskManager = Ext.TaskManager = new b();
    b.Task = new Ext.Class({isTask: true, stopped: true, fireOnStart: false, constructor: function (c) {
        Ext.apply(this, c)
    }, restart: function (c) {
        if (c !== undefined) {
            this.interval = c
        }
        this.manager.start(this)
    }, start: function (c) {
        if (this.stopped) {
            this.restart(c)
        }
    }, stop: function () {
        this.manager.stop(this)
    }});
    a = b.Task.prototype;
    a.destroy = a.stop
});
Ext.define("Ext.perf.Accumulator", (function () {
    var c = null, h = Ext.global.chrome, d, b = function () {
        b = function () {
            return new Date().getTime()
        };
        var l, m;
        if (Ext.isChrome && h && h.Interval) {
            l = new h.Interval();
            l.start();
            b = function () {
                return l.microseconds() / 1000
            }
        } else {
            if (window.ActiveXObject) {
                try {
                    m = new ActiveXObject("SenchaToolbox.Toolbox");
                    Ext.senchaToolbox = m;
                    b = function () {
                        return m.milliseconds
                    }
                } catch (n) {
                }
            } else {
                if (Date.now) {
                    b = Date.now
                }
            }
        }
        Ext.perf.getTimestamp = Ext.perf.Accumulator.getTimestamp = b;
        return b()
    };

    function i(m, l) {
        m.sum += l;
        m.min = Math.min(m.min, l);
        m.max = Math.max(m.max, l)
    }

    function e(o) {
        var m = o ? o : (b() - this.time), n = this, l = n.accum;
        ++l.count;
        if (!--l.depth) {
            i(l.total, m)
        }
        i(l.pure, m - n.childTime);
        c = n.parent;
        if (c) {
            ++c.accum.childCount;
            c.childTime += m
        }
    }

    function a() {
        return{min: Number.MAX_VALUE, max: 0, sum: 0}
    }

    function j(m, l) {
        return function () {
            var o = m.enter(), n = l.apply(this, arguments);
            o.leave();
            return n
        }
    }

    function k(l) {
        return Math.round(l * 100) / 100
    }

    function g(n, m, l, p) {
        var o = {avg: 0, min: p.min, max: p.max, sum: 0};
        if (n) {
            l = l || 0;
            o.sum = p.sum - m * l;
            o.avg = o.sum / n
        }
        return o
    }

    return{constructor: function (l) {
        var m = this;
        m.count = m.childCount = m.depth = m.maxDepth = 0;
        m.pure = a();
        m.total = a();
        m.name = l
    }, statics: {getTimestamp: b}, format: function (l) {
        if (!d) {
            d = new Ext.XTemplate(["{name} - {count} call(s)", '<tpl if="count">', '<tpl if="childCount">', " ({childCount} children)", "</tpl>", '<tpl if="depth - 1">', " ({depth} deep)", "</tpl>", '<tpl for="times">', ", {type}: {[this.time(values.sum)]} msec (", "avg={[this.time(values.sum / parent.count)]}", ")", "</tpl>", "</tpl>"].join(""), {time: function (n) {
                return Math.round(n * 100) / 100
            }})
        }
        var m = this.getData(l);
        m.name = this.name;
        m.pure.type = "Pure";
        m.total.type = "Total";
        m.times = [m.pure, m.total];
        return d.apply(m)
    }, getData: function (l) {
        var m = this;
        return{count: m.count, childCount: m.childCount, depth: m.maxDepth, pure: g(m.count, m.childCount, l, m.pure), total: g(m.count, m.childCount, l, m.total)}
    }, enter: function () {
        var l = this, m = {accum: l, leave: e, childTime: 0, parent: c};
        ++l.depth;
        if (l.maxDepth < l.depth) {
            l.maxDepth = l.depth
        }
        c = m;
        m.time = b();
        return m
    }, monitor: function (n, m, l) {
        var o = this.enter();
        if (l) {
            n.apply(m, l)
        } else {
            n.call(m)
        }
        o.leave()
    }, report: function () {
        Ext.log(this.format())
    }, tap: function (t, v) {
        var u = this, o = typeof v == "string" ? [v] : v, s, w, q, p, n, m, l, r;
        r = function () {
            if (typeof t == "string") {
                s = Ext.global;
                p = t.split(".");
                for (q = 0, n = p.length; q < n; ++q) {
                    s = s[p[q]]
                }
            } else {
                s = t
            }
            for (q = 0, n = o.length; q < n; ++q) {
                m = o[q];
                w = m.charAt(0) == "!";
                if (w) {
                    m = m.substring(1)
                } else {
                    w = !(m in s.prototype)
                }
                l = w ? s : s.prototype;
                l[m] = j(u, l[m])
            }
        };
        Ext.ClassManager.onCreated(r, u, t);
        return u
    }}
}()), function () {
    Ext.perf.getTimestamp = this.getTimestamp
});
Ext.define("Ext.perf.Monitor", {singleton: true, alternateClassName: "Ext.Perf", requires: ["Ext.perf.Accumulator"], constructor: function () {
    this.accumulators = [];
    this.accumulatorsByName = {}
}, calibrate: function () {
    var b = new Ext.perf.Accumulator("$"), g = b.total, c = Ext.perf.Accumulator.getTimestamp, e = 0, h, a, d;
    d = c();
    do {
        h = b.enter();
        h.leave();
        ++e
    } while (g.sum < 100);
    a = c();
    return(a - d) / e
}, get: function (b) {
    var c = this, a = c.accumulatorsByName[b];
    if (!a) {
        c.accumulatorsByName[b] = a = new Ext.perf.Accumulator(b);
        c.accumulators.push(a)
    }
    return a
}, enter: function (a) {
    return this.get(a).enter()
}, monitor: function (a, c, b) {
    this.get(a).monitor(c, b)
}, report: function () {
    var c = this, b = c.accumulators, a = c.calibrate();
    b.sort(function (e, d) {
        return(e.name < d.name) ? -1 : ((d.name < e.name) ? 1 : 0)
    });
    c.updateGC();
    Ext.log("Calibration: " + Math.round(a * 100) / 100 + " msec/sample");
    Ext.each(b, function (d) {
        Ext.log(d.format(a))
    })
}, getData: function (c) {
    var b = {}, a = this.accumulators;
    Ext.each(a, function (d) {
        if (c || d.count) {
            b[d.name] = d.getData()
        }
    });
    return b
}, reset: function () {
    Ext.each(this.accumulators, function (a) {
        var b = a;
        b.count = b.childCount = b.depth = b.maxDepth = 0;
        b.pure = {min: Number.MAX_VALUE, max: 0, sum: 0};
        b.total = {min: Number.MAX_VALUE, max: 0, sum: 0}
    })
}, updateGC: function () {
    var a = this.accumulatorsByName.GC, b = Ext.senchaToolbox, c;
    if (a) {
        a.count = b.garbageCollectionCounter || 0;
        if (a.count) {
            c = a.pure;
            a.total.sum = c.sum = b.garbageCollectionMilliseconds;
            c.min = c.max = c.sum / a.count;
            c = a.total;
            c.min = c.max = c.sum / a.count
        }
    }
}, watchGC: function () {
    Ext.perf.getTimestamp();
    var a = Ext.senchaToolbox;
    if (a) {
        this.get("GC");
        a.watchGarbageCollector(false)
    }
}, setup: function (c) {
    if (!c) {
        c = {render: {"Ext.AbstractComponent": "render"}, layout: {"Ext.layout.Context": "run"}}
    }
    this.currentConfig = c;
    var d, g, b, e, a;
    for (d in c) {
        if (c.hasOwnProperty(d)) {
            g = c[d];
            b = Ext.Perf.get(d);
            for (e in g) {
                if (g.hasOwnProperty(e)) {
                    a = g[e];
                    b.tap(e, a)
                }
            }
        }
    }
    this.watchGC()
}});
Ext.is = {init: function (b) {
    var c = this.platforms, e = c.length, d, a;
    b = b || window.navigator;
    for (d = 0; d < e; d++) {
        a = c[d];
        this[a.identity] = a.regex.test(b[a.property])
    }
    this.Desktop = this.Mac || this.Windows || (this.Linux && !this.Android);
    this.Tablet = this.iPad;
    this.Phone = !this.Desktop && !this.Tablet;
    this.iOS = this.iPhone || this.iPad || this.iPod;
    this.Standalone = !!window.navigator.standalone
}, platforms: [
    {property: "platform", regex: /iPhone/i, identity: "iPhone"},
    {property: "platform", regex: /iPod/i, identity: "iPod"},
    {property: "userAgent", regex: /iPad/i, identity: "iPad"},
    {property: "userAgent", regex: /Blackberry/i, identity: "Blackberry"},
    {property: "userAgent", regex: /Android/i, identity: "Android"},
    {property: "platform", regex: /Mac/i, identity: "Mac"},
    {property: "platform", regex: /Win/i, identity: "Windows"},
    {property: "platform", regex: /Linux/i, identity: "Linux"}
]};
Ext.is.init();
(function () {
    var a = function (d, c) {
        var b = d.ownerDocument.defaultView, e = (b ? b.getComputedStyle(d, null) : d.currentStyle) || d.style;
        return e[c]
    };
    Ext.supports = {init: function () {
        var d = this, e = document, c = d.tests, i = c.length, h = i && Ext.isReady && e.createElement("div"), g, b = [];
        if (h) {
            h.innerHTML = ['<div style="height:30px;width:50px;">', '<div style="height:20px;width:20px;"></div>', "</div>", '<div style="width: 200px; height: 200px; position: relative; padding: 5px;">', '<div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;"></div>', "</div>", '<div style="position: absolute; left: 10%; top: 10%;"></div>', '<div style="float:left; background-color:transparent;"></div>'].join("");
            e.body.appendChild(h)
        }
        while (i--) {
            g = c[i];
            if (h || g.early) {
                d[g.identity] = g.fn.call(d, e, h)
            } else {
                b.push(g)
            }
        }
        if (h) {
            e.body.removeChild(h)
        }
        d.tests = b
    }, PointerEvents: "pointerEvents" in document.documentElement.style, CSS3BoxShadow: "boxShadow" in document.documentElement.style || "WebkitBoxShadow" in document.documentElement.style || "MozBoxShadow" in document.documentElement.style, ClassList: !!document.documentElement.classList, OrientationChange: ((typeof window.orientation != "undefined") && ("onorientationchange" in window)), DeviceMotion: ("ondevicemotion" in window), Touch: ("ontouchstart" in window) && (!Ext.is.Desktop), TimeoutActualLateness: (function () {
        setTimeout(function () {
            Ext.supports.TimeoutActualLateness = arguments.length !== 0
        }, 0)
    }()), tests: [
        {identity: "Transitions", fn: function (h, k) {
            var g = ["webkit", "Moz", "o", "ms", "khtml"], j = "TransitionEnd", b = [g[0] + j, "transitionend", g[2] + j, g[3] + j, g[4] + j], e = g.length, d = 0, c = false;
            for (; d < e; d++) {
                if (a(k, g[d] + "TransitionProperty")) {
                    Ext.supports.CSS3Prefix = g[d];
                    Ext.supports.CSS3TransitionEnd = b[d];
                    c = true;
                    break
                }
            }
            return c
        }},
        {identity: "RightMargin", fn: function (c, d) {
            var b = c.defaultView;
            return !(b && b.getComputedStyle(d.firstChild.firstChild, null).marginRight != "0px")
        }},
        {identity: "DisplayChangeInputSelectionBug", early: true, fn: function () {
            var b = Ext.webKitVersion;
            return 0 < b && b < 533
        }},
        {identity: "DisplayChangeTextAreaSelectionBug", early: true, fn: function () {
            var b = Ext.webKitVersion;
            return 0 < b && b < 534.24
        }},
        {identity: "TransparentColor", fn: function (c, d, b) {
            b = c.defaultView;
            return !(b && b.getComputedStyle(d.lastChild, null).backgroundColor != "transparent")
        }},
        {identity: "ComputedStyle", fn: function (c, d, b) {
            b = c.defaultView;
            return b && b.getComputedStyle
        }},
        {identity: "Svg", fn: function (b) {
            return !!b.createElementNS && !!b.createElementNS("http://www.w3.org/2000/svg", "svg").createSVGRect
        }},
        {identity: "Canvas", fn: function (b) {
            return !!b.createElement("canvas").getContext
        }},
        {identity: "Vml", fn: function (b) {
            var c = b.createElement("div");
            c.innerHTML = "<!--[if vml]><br/><br/><![endif]-->";
            return(c.childNodes.length == 2)
        }},
        {identity: "Float", fn: function (b, c) {
            return !!c.lastChild.style.cssFloat
        }},
        {identity: "AudioTag", fn: function (b) {
            return !!b.createElement("audio").canPlayType
        }},
        {identity: "History", fn: function () {
            var b = window.history;
            return !!(b && b.pushState)
        }},
        {identity: "CSS3DTransform", fn: function () {
            return(typeof WebKitCSSMatrix != "undefined" && new WebKitCSSMatrix().hasOwnProperty("m41"))
        }},
        {identity: "CSS3LinearGradient", fn: function (h, j) {
            var g = "background-image:", d = "-webkit-gradient(linear, left top, right bottom, from(black), to(white))", i = "linear-gradient(left top, black, white)", e = "-moz-" + i, b = "-o-" + i, c = [g + d, g + i, g + e, g + b];
            j.style.cssText = c.join(";");
            return("" + j.style.backgroundImage).indexOf("gradient") !== -1
        }},
        {identity: "CSS3BorderRadius", fn: function (e, g) {
            var c = ["borderRadius", "BorderRadius", "MozBorderRadius", "WebkitBorderRadius", "OBorderRadius", "KhtmlBorderRadius"], d = false, b;
            for (b = 0; b < c.length; b++) {
                if (document.body.style[c[b]] !== undefined) {
                    return true
                }
            }
            return d
        }},
        {identity: "GeoLocation", fn: function () {
            return(typeof navigator != "undefined" && typeof navigator.geolocation != "undefined") || (typeof google != "undefined" && typeof google.gears != "undefined")
        }},
        {identity: "MouseEnterLeave", fn: function (b, c) {
            return("onmouseenter" in c && "onmouseleave" in c)
        }},
        {identity: "MouseWheel", fn: function (b, c) {
            return("onmousewheel" in c)
        }},
        {identity: "Opacity", fn: function (b, c) {
            if (Ext.isIE6 || Ext.isIE7 || Ext.isIE8) {
                return false
            }
            c.firstChild.style.cssText = "opacity:0.73";
            return c.firstChild.style.opacity == "0.73"
        }},
        {identity: "Placeholder", fn: function (b) {
            return"placeholder" in b.createElement("input")
        }},
        {identity: "Direct2DBug", fn: function () {
            return Ext.isString(document.body.style.msTransformOrigin)
        }},
        {identity: "BoundingClientRect", fn: function (b, c) {
            return Ext.isFunction(c.getBoundingClientRect)
        }},
        {identity: "IncludePaddingInWidthCalculation", fn: function (b, c) {
            return c.childNodes[1].firstChild.offsetWidth == 210
        }},
        {identity: "IncludePaddingInHeightCalculation", fn: function (b, c) {
            return c.childNodes[1].firstChild.offsetHeight == 210
        }},
        {identity: "ArraySort", fn: function () {
            var b = [1, 2, 3, 4, 5].sort(function () {
                return 0
            });
            return b[0] === 1 && b[1] === 2 && b[2] === 3 && b[3] === 4 && b[4] === 5
        }},
        {identity: "Range", fn: function () {
            return !!document.createRange
        }},
        {identity: "CreateContextualFragment", fn: function () {
            var b = Ext.supports.Range ? document.createRange() : false;
            return b && !!b.createContextualFragment
        }},
        {identity: "WindowOnError", fn: function () {
            return Ext.isIE || Ext.isGecko || Ext.webKitVersion >= 534.16
        }},
        {identity: "TextAreaMaxLength", fn: function () {
            var b = document.createElement("textarea");
            return("maxlength" in b)
        }},
        {identity: "GetPositionPercentage", fn: function (b, c) {
            return a(c.childNodes[2], "left") == "10%"
        }}
    ]}
}());
Ext.supports.init();
Ext.util.DelayedTask = function (d, c, a) {
    var e = this, g, b = function () {
        clearInterval(g);
        g = null;
        d.apply(c, a || [])
    };
    this.delay = function (i, k, j, h) {
        e.cancel();
        d = k || d;
        c = j || c;
        a = h || a;
        g = setInterval(b, i)
    };
    this.cancel = function () {
        if (g) {
            clearInterval(g);
            g = null
        }
    }
};
Ext.require("Ext.util.DelayedTask", function () {
    Ext.util.Event = Ext.extend(Object, (function () {
        var b = {};

        function d(h, i, j, g) {
            return function () {
                if (j.target === arguments[0]) {
                    h.apply(g, arguments)
                }
            }
        }

        function c(h, i, j, g) {
            i.task = new Ext.util.DelayedTask();
            return function () {
                i.task.delay(j.buffer, h, g, Ext.Array.toArray(arguments))
            }
        }

        function a(h, i, j, g) {
            return function () {
                var k = new Ext.util.DelayedTask();
                if (!i.tasks) {
                    i.tasks = []
                }
                i.tasks.push(k);
                k.delay(j.delay || 10, h, g, Ext.Array.toArray(arguments))
            }
        }

        function e(h, i, j, g) {
            return function () {
                var k = i.ev;
                if (k.removeListener(i.fn, g) && k.observable) {
                    k.observable.hasListeners[k.name]--
                }
                return h.apply(g, arguments)
            }
        }

        return{isEvent: true, constructor: function (h, g) {
            this.name = g;
            this.observable = h;
            this.listeners = []
        }, addListener: function (i, h, g) {
            var j = this, k;
            h = h || j.observable;
            if (!j.isListening(i, h)) {
                k = j.createListener(i, h, g);
                if (j.firing) {
                    j.listeners = j.listeners.slice(0)
                }
                j.listeners.push(k)
            }
        }, createListener: function (j, i, g) {
            g = g || b;
            i = i || this.observable;
            var k = {fn: j, scope: i, o: g, ev: this}, h = j;
            if (g.single) {
                h = e(h, k, g, i)
            }
            if (g.target) {
                h = d(h, k, g, i)
            }
            if (g.delay) {
                h = a(h, k, g, i)
            }
            if (g.buffer) {
                h = c(h, k, g, i)
            }
            k.fireFn = h;
            return k
        }, findListener: function (l, k) {
            var j = this.listeners, g = j.length, m, h;
            while (g--) {
                m = j[g];
                if (m) {
                    h = m.scope;
                    if (m.fn == l && (h == (k || this.observable))) {
                        return g
                    }
                }
            }
            return -1
        }, isListening: function (h, g) {
            return this.findListener(h, g) !== -1
        }, removeListener: function (j, i) {
            var l = this, h, m, g;
            h = l.findListener(j, i);
            if (h != -1) {
                m = l.listeners[h];
                if (l.firing) {
                    l.listeners = l.listeners.slice(0)
                }
                if (m.task) {
                    m.task.cancel();
                    delete m.task
                }
                g = m.tasks && m.tasks.length;
                if (g) {
                    while (g--) {
                        m.tasks[g].cancel()
                    }
                    delete m.tasks
                }
                Ext.Array.erase(l.listeners, h, 1);
                return true
            }
            return false
        }, clearListeners: function () {
            var h = this.listeners, g = h.length;
            while (g--) {
                this.removeListener(h[g].fn, h[g].scope)
            }
        }, fire: function () {
            var l = this, j = l.listeners, k = j.length, h, g, m;
            if (k > 0) {
                l.firing = true;
                for (h = 0; h < k; h++) {
                    m = j[h];
                    g = arguments.length ? Array.prototype.slice.call(arguments, 0) : [];
                    if (m.o) {
                        g.push(m.o)
                    }
                    if (m && m.fireFn.apply(m.scope || l.observable, g) === false) {
                        return(l.firing = false)
                    }
                }
            }
            l.firing = false;
            return true
        }}
    }()))
});
Ext.EventManager = new function () {
    var a = this, d = document, c = window, b = function () {
        var k = d.body || d.getElementsByTagName("body")[0], i = Ext.baseCSSPrefix, o = [i + "body"], g = [], m = Ext.supports.CSS3LinearGradient, l = Ext.supports.CSS3BorderRadius, h = [], j, e;
        if (!k) {
            return false
        }
        j = k.parentNode;
        function n(p) {
            o.push(i + p)
        }

        if (Ext.isIE) {
            n("ie");
            if (Ext.isIE6) {
                n("ie6")
            } else {
                n("ie7p");
                if (Ext.isIE7) {
                    n("ie7")
                } else {
                    n("ie8p");
                    if (Ext.isIE8) {
                        n("ie8")
                    } else {
                        n("ie9p");
                        if (Ext.isIE9) {
                            n("ie9")
                        }
                    }
                }
            }
            if (Ext.isIE6 || Ext.isIE7) {
                n("ie7m")
            }
            if (Ext.isIE6 || Ext.isIE7 || Ext.isIE8) {
                n("ie8m")
            }
            if (Ext.isIE7 || Ext.isIE8) {
                n("ie78")
            }
        }
        if (Ext.isGecko) {
            n("gecko");
            if (Ext.isGecko3) {
                n("gecko3")
            }
            if (Ext.isGecko4) {
                n("gecko4")
            }
            if (Ext.isGecko5) {
                n("gecko5")
            }
        }
        if (Ext.isOpera) {
            n("opera")
        }
        if (Ext.isWebKit) {
            n("webkit")
        }
        if (Ext.isSafari) {
            n("safari");
            if (Ext.isSafari2) {
                n("safari2")
            }
            if (Ext.isSafari3) {
                n("safari3")
            }
            if (Ext.isSafari4) {
                n("safari4")
            }
            if (Ext.isSafari5) {
                n("safari5")
            }
            if (Ext.isSafari5_0) {
                n("safari5_0")
            }
        }
        if (Ext.isChrome) {
            n("chrome")
        }
        if (Ext.isMac) {
            n("mac")
        }
        if (Ext.isLinux) {
            n("linux")
        }
        if (!l) {
            n("nbr")
        }
        if (!m) {
            n("nlg")
        }
        if (Ext.scopeResetCSS) {
            e = Ext.resetElementSpec = {cls: i + "reset"};
            if (!m) {
                h.push(i + "nlg")
            }
            if (!l) {
                h.push(i + "nbr")
            }
            if (h.length) {
                e.cn = {cls: h.join(" ")}
            }
            Ext.resetElement = Ext.getBody().createChild(e);
            if (h.length) {
                Ext.resetElement = Ext.get(Ext.resetElement.dom.firstChild)
            }
        } else {
            Ext.resetElement = Ext.getBody();
            n("reset")
        }
        if (j) {
            if (Ext.isStrict && (Ext.isIE6 || Ext.isIE7)) {
                Ext.isBorderBox = false
            } else {
                Ext.isBorderBox = true
            }
            if (Ext.isBorderBox) {
                g.push(i + "border-box")
            }
            if (Ext.isStrict) {
                g.push(i + "strict")
            } else {
                g.push(i + "quirks")
            }
            Ext.fly(j, "_internal").addCls(g)
        }
        Ext.fly(k, "_internal").addCls(o);
        return true
    };
    Ext.apply(a, {hasBoundOnReady: false, hasFiredReady: false, deferReadyEvent: 1, onReadyChain: [], readyEvent: (function () {
        var e = new Ext.util.Event();
        e.fire = function () {
            Ext._beforeReadyTime = Ext._beforeReadyTime || new Date().getTime();
            e.self.prototype.fire.apply(e, arguments);
            Ext._afterReadytime = new Date().getTime()
        };
        return e
    }()), idleEvent: new Ext.util.Event(), isReadyPaused: function () {
        return(/[?&]ext-pauseReadyFire\b/i.test(location.search) && !Ext._continueFireReady)
    }, bindReadyEvent: function () {
        if (a.hasBoundOnReady) {
            return
        }
        if (d.readyState == "complete") {
            a.onReadyEvent({type: d.readyState || "body"})
        } else {
            document.addEventListener("DOMContentLoaded", a.onReadyEvent, false);
            window.addEventListener("load", a.onReadyEvent, false);
            a.hasBoundOnReady = true
        }
    }, onReadyEvent: function (g) {
        if (g && g.type) {
            a.onReadyChain.push(g.type)
        }
        if (a.hasBoundOnReady) {
            document.removeEventListener("DOMContentLoaded", a.onReadyEvent, false);
            window.removeEventListener("load", a.onReadyEvent, false)
        }
        if (!Ext.isReady) {
            a.fireDocReady()
        }
    }, fireDocReady: function () {
        if (!Ext.isReady) {
            Ext._readyTime = new Date().getTime();
            Ext.isReady = true;
            Ext.supports.init();
            a.onWindowUnload();
            a.readyEvent.onReadyChain = a.onReadyChain;
            if (Ext.isNumber(a.deferReadyEvent)) {
                Ext.Function.defer(a.fireReadyEvent, a.deferReadyEvent);
                a.hasDocReadyTimer = true
            } else {
                a.fireReadyEvent()
            }
        }
    }, fireReadyEvent: function () {
        var e = a.readyEvent;
        a.hasDocReadyTimer = false;
        a.isFiring = true;
        while (e.listeners.length && !a.isReadyPaused()) {
            e.fire()
        }
        a.isFiring = false;
        a.hasFiredReady = true
    }, onDocumentReady: function (h, g, e) {
        e = e || {};
        e.single = true;
        a.readyEvent.addListener(h, g, e);
        if (!(a.isFiring || a.hasDocReadyTimer)) {
            if (Ext.isReady) {
                a.fireReadyEvent()
            } else {
                a.bindReadyEvent()
            }
        }
    }, stoppedMouseDownEvent: new Ext.util.Event(), propRe: /^(?:scope|delay|buffer|single|stopEvent|preventDefault|stopPropagation|normalized|args|delegate|freezeEvent)$/, getId: function (e) {
        var g;
        e = Ext.getDom(e);
        if (e === d || e === c) {
            g = e === d ? Ext.documentId : Ext.windowId
        } else {
            g = Ext.id(e)
        }
        if (!Ext.cache[g]) {
            Ext.addCacheEntry(g, null, e)
        }
        return g
    }, prepareListenerConfig: function (i, g, k) {
        var l = a.propRe, h, j, e;
        for (h in g) {
            if (g.hasOwnProperty(h)) {
                if (!l.test(h)) {
                    j = g[h];
                    if (typeof j == "function") {
                        e = [i, h, j, g.scope, g]
                    } else {
                        e = [i, h, j.fn, j.scope, j]
                    }
                    if (k) {
                        a.removeListener.apply(a, e)
                    } else {
                        a.addListener.apply(a, e)
                    }
                }
            }
        }
    }, mouseEnterLeaveRe: /mouseenter|mouseleave/, normalizeEvent: function (e, g) {
        if (a.mouseEnterLeaveRe.test(e) && !Ext.supports.MouseEnterLeave) {
            if (g) {
                g = Ext.Function.createInterceptor(g, a.contains)
            }
            e = e == "mouseenter" ? "mouseover" : "mouseout"
        } else {
            if (e == "mousewheel" && !Ext.supports.MouseWheel && !Ext.isOpera) {
                e = "DOMMouseScroll"
            }
        }
        return{eventName: e, fn: g}
    }, contains: function (g) {
        var e = g.browserEvent.currentTarget, h = a.getRelatedTarget(g);
        if (e && e.firstChild) {
            while (h) {
                if (h === e) {
                    return false
                }
                h = h.parentNode;
                if (h && (h.nodeType != 1)) {
                    h = null
                }
            }
        }
        return true
    }, addListener: function (h, e, k, j, g) {
        if (typeof e !== "string") {
            a.prepareListenerConfig(h, e);
            return
        }
        var l = h.dom || Ext.getDom(h), m, i;
        g = g || {};
        m = a.normalizeEvent(e, k);
        i = a.createListenerWrap(l, e, m.fn, j, g);
        if (l.attachEvent) {
            l.attachEvent("on" + m.eventName, i)
        } else {
            l.addEventListener(m.eventName, i, g.capture || false)
        }
        if (l == d && e == "mousedown") {
            a.stoppedMouseDownEvent.addListener(i)
        }
        a.getEventListenerCache(h.dom ? h : l, e).push({fn: k, wrap: i, scope: j})
    }, removeListener: function (p, q, r, t) {
        if (typeof q !== "string") {
            a.prepareListenerConfig(p, q, true);
            return
        }
        var n = Ext.getDom(p), h = p.dom ? p : Ext.get(n), e = a.getEventListenerCache(h, q), s = a.normalizeEvent(q).eventName, o = e.length, m, k, g, l;
        while (o--) {
            k = e[o];
            if (k && (!r || k.fn == r) && (!t || k.scope === t)) {
                g = k.wrap;
                if (g.task) {
                    clearTimeout(g.task);
                    delete g.task
                }
                m = g.tasks && g.tasks.length;
                if (m) {
                    while (m--) {
                        clearTimeout(g.tasks[m])
                    }
                    delete g.tasks
                }
                if (n.detachEvent) {
                    n.detachEvent("on" + s, g)
                } else {
                    n.removeEventListener(s, g, false)
                }
                if (g && n == d && q == "mousedown") {
                    a.stoppedMouseDownEvent.removeListener(g)
                }
                Ext.Array.erase(e, o, 1)
            }
        }
    }, removeAll: function (i) {
        var j = i.dom ? i : Ext.get(i), g, h, e;
        if (!j) {
            return
        }
        g = (j.$cache || j.getCache());
        h = g.events;
        for (e in h) {
            if (h.hasOwnProperty(e)) {
                a.removeListener(j, e)
            }
        }
        g.events = {}
    }, purgeElement: function (j, g) {
        var k = Ext.getDom(j), h = 0, e;
        if (g) {
            a.removeListener(j, g)
        } else {
            a.removeAll(j)
        }
        if (k && k.childNodes) {
            for (e = j.childNodes.length; h < e; h++) {
                a.purgeElement(j.childNodes[h], g)
            }
        }
    }, createListenerWrap: function (i, h, l, m, n) {
        n = n || {};
        var k, j, e = /\\/g, g = function (p, o) {
            if (!j) {
                k = ["if(!" + Ext.name + ") {return;}"];
                if (n.buffer || n.delay || n.freezeEvent) {
                    k.push("e = new X.EventObjectImpl(e, " + (n.freezeEvent ? "true" : "false") + ");")
                } else {
                    k.push("e = X.EventObject.setEvent(e);")
                }
                if (n.delegate) {
                    k.push('var result, t = e.getTarget("' + (n.delegate + "").replace(e, "\\\\") + '", this);');
                    k.push("if(!t) {return;}")
                } else {
                    k.push("var t = e.target, result;")
                }
                if (n.target) {
                    k.push("if(e.target !== options.target) {return;}")
                }
                if (n.stopEvent) {
                    k.push("e.stopEvent();")
                } else {
                    if (n.preventDefault) {
                        k.push("e.preventDefault();")
                    }
                    if (n.stopPropagation) {
                        k.push("e.stopPropagation();")
                    }
                }
                if (n.normalized === false) {
                    k.push("e = e.browserEvent;")
                }
                if (n.buffer) {
                    k.push("(wrap.task && clearTimeout(wrap.task));");
                    k.push("wrap.task = setTimeout(function() {")
                }
                if (n.delay) {
                    k.push("wrap.tasks = wrap.tasks || [];");
                    k.push("wrap.tasks.push(setTimeout(function() {")
                }
                k.push("result = fn.call(scope || dom, e, t, options);");
                if (n.single) {
                    k.push("evtMgr.removeListener(dom, ename, fn, scope);")
                }
                if (h !== "mousemove") {
                    k.push("if (evtMgr.idleEvent.listeners.length) {");
                    k.push("evtMgr.idleEvent.fire();");
                    k.push("}")
                }
                if (n.delay) {
                    k.push("}, " + n.delay + "));")
                }
                if (n.buffer) {
                    k.push("}, " + n.buffer + ");")
                }
                k.push("return result;");
                j = Ext.cacheableFunctionFactory("e", "options", "fn", "scope", "ename", "dom", "wrap", "args", "X", "evtMgr", k.join("\n"))
            }
            return j.call(i, p, n, l, m, h, i, g, o, Ext, a)
        };
        return g
    }, getEventListenerCache: function (i, e) {
        var h, g;
        if (!i) {
            return[]
        }
        if (i.$cache) {
            h = i.$cache
        } else {
            h = Ext.cache[a.getId(i)]
        }
        g = h.events || (h.events = {});
        return g[e] || (g[e] = [])
    }, mouseLeaveRe: /(mouseout|mouseleave)/, mouseEnterRe: /(mouseover|mouseenter)/, stopEvent: function (e) {
        a.stopPropagation(e);
        a.preventDefault(e)
    }, stopPropagation: function (e) {
        e = e.browserEvent || e;
        if (e.stopPropagation) {
            e.stopPropagation()
        } else {
            e.cancelBubble = true
        }
    }, preventDefault: function (g) {
        g = g.browserEvent || g;
        if (g.preventDefault) {
            g.preventDefault()
        } else {
            g.returnValue = false;
            try {
                if (g.ctrlKey || g.keyCode > 111 && g.keyCode < 124) {
                    g.keyCode = -1
                }
            } catch (h) {
            }
        }
    }, getRelatedTarget: function (e) {
        e = e.browserEvent || e;
        var g = e.relatedTarget;
        if (!g) {
            if (a.mouseLeaveRe.test(e.type)) {
                g = e.toElement
            } else {
                if (a.mouseEnterRe.test(e.type)) {
                    g = e.fromElement
                }
            }
        }
        return a.resolveTextNode(g)
    }, getPageX: function (e) {
        return a.getPageXY(e)[0]
    }, getPageY: function (e) {
        return a.getPageXY(e)[1]
    }, getPageXY: function (h) {
        h = h.browserEvent || h;
        var g = h.pageX, j = h.pageY, i = d.documentElement, e = d.body;
        if (!g && g !== 0) {
            g = h.clientX + (i && i.scrollLeft || e && e.scrollLeft || 0) - (i && i.clientLeft || e && e.clientLeft || 0);
            j = h.clientY + (i && i.scrollTop || e && e.scrollTop || 0) - (i && i.clientTop || e && e.clientTop || 0)
        }
        return[g, j]
    }, getTarget: function (e) {
        e = e.browserEvent || e;
        return a.resolveTextNode(e.target || e.srcElement)
    }, resolveTextNode: Ext.isGecko ? function (g) {
        if (!g) {
            return
        }
        var e = HTMLElement.prototype.toString.call(g);
        if (e == "[xpconnect wrapped native prototype]" || e == "[object XULElement]") {
            return
        }
        return g.nodeType == 3 ? g.parentNode : g
    } : function (e) {
        return e && e.nodeType == 3 ? e.parentNode : e
    }, curWidth: 0, curHeight: 0, onWindowResize: function (i, h, g) {
        var e = a.resizeEvent;
        if (!e) {
            a.resizeEvent = e = new Ext.util.Event();
            a.on(c, "resize", a.fireResize, null, {buffer: 100})
        }
        e.addListener(i, h, g)
    }, fireResize: function () {
        var e = Ext.Element.getViewWidth(), g = Ext.Element.getViewHeight();
        if (a.curHeight != g || a.curWidth != e) {
            a.curHeight = g;
            a.curWidth = e;
            a.resizeEvent.fire(e, g)
        }
    }, removeResizeListener: function (h, g) {
        var e = a.resizeEvent;
        if (e) {
            e.removeListener(h, g)
        }
    }, onWindowUnload: function (i, h, g) {
        var e = a.unloadEvent;
        if (!e) {
            a.unloadEvent = e = new Ext.util.Event();
            a.addListener(c, "unload", a.fireUnload)
        }
        if (i) {
            e.addListener(i, h, g)
        }
    }, fireUnload: function () {
        try {
            d = c = undefined;
            var m, h, k, j, g;
            a.unloadEvent.fire();
            if (Ext.isGecko3) {
                m = Ext.ComponentQuery.query("gridview");
                h = 0;
                k = m.length;
                for (; h < k; h++) {
                    m[h].scrollToTop()
                }
            }
            g = Ext.cache;
            for (j in g) {
                if (g.hasOwnProperty(j)) {
                    a.removeAll(j)
                }
            }
        } catch (l) {
        }
    }, removeUnloadListener: function (h, g) {
        var e = a.unloadEvent;
        if (e) {
            e.removeListener(h, g)
        }
    }, useKeyDown: Ext.isWebKit ? parseInt(navigator.userAgent.match(/AppleWebKit\/(\d+)/)[1], 10) >= 525 : !((Ext.isGecko && !Ext.isWindows) || Ext.isOpera), getKeyEvent: function () {
        return a.useKeyDown ? "keydown" : "keypress"
    }});
    if (!("addEventListener" in document) && document.attachEvent) {
        Ext.apply(a, {pollScroll: function () {
            var g = true;
            try {
                document.documentElement.doScroll("left")
            } catch (h) {
                g = false
            }
            if (g && document.body) {
                a.onReadyEvent({type: "doScroll"})
            } else {
                a.scrollTimeout = setTimeout(a.pollScroll, 20)
            }
            return g
        }, scrollTimeout: null, readyStatesRe: /complete/i, checkReadyState: function () {
            var e = document.readyState;
            if (a.readyStatesRe.test(e)) {
                a.onReadyEvent({type: e})
            }
        }, bindReadyEvent: function () {
            var g = true;
            if (a.hasBoundOnReady) {
                return
            }
            try {
                g = window.frameElement === undefined
            } catch (h) {
                g = false
            }
            if (!g || !d.documentElement.doScroll) {
                a.pollScroll = Ext.emptyFn
            }
            if (a.pollScroll() === true) {
                return
            }
            if (d.readyState == "complete") {
                a.onReadyEvent({type: "already " + (d.readyState || "body")})
            } else {
                d.attachEvent("onreadystatechange", a.checkReadyState);
                window.attachEvent("onload", a.onReadyEvent);
                a.hasBoundOnReady = true
            }
        }, onReadyEvent: function (g) {
            if (g && g.type) {
                a.onReadyChain.push(g.type)
            }
            if (a.hasBoundOnReady) {
                document.detachEvent("onreadystatechange", a.checkReadyState);
                window.detachEvent("onload", a.onReadyEvent)
            }
            if (Ext.isNumber(a.scrollTimeout)) {
                clearTimeout(a.scrollTimeout);
                delete a.scrollTimeout
            }
            if (!Ext.isReady) {
                a.fireDocReady()
            }
        }, onReadyChain: []})
    }
    Ext.onReady = function (h, g, e) {
        Ext.Loader.onReady(h, g, true, e)
    };
    Ext.onDocumentReady = a.onDocumentReady;
    a.on = a.addListener;
    a.un = a.removeListener;
    Ext.onReady(b)
};
Ext.define("Ext.EventObjectImpl", {uses: ["Ext.util.Point"], BACKSPACE: 8, TAB: 9, NUM_CENTER: 12, ENTER: 13, RETURN: 13, SHIFT: 16, CTRL: 17, ALT: 18, PAUSE: 19, CAPS_LOCK: 20, ESC: 27, SPACE: 32, PAGE_UP: 33, PAGE_DOWN: 34, END: 35, HOME: 36, LEFT: 37, UP: 38, RIGHT: 39, DOWN: 40, PRINT_SCREEN: 44, INSERT: 45, DELETE: 46, ZERO: 48, ONE: 49, TWO: 50, THREE: 51, FOUR: 52, FIVE: 53, SIX: 54, SEVEN: 55, EIGHT: 56, NINE: 57, A: 65, B: 66, C: 67, D: 68, E: 69, F: 70, G: 71, H: 72, I: 73, J: 74, K: 75, L: 76, M: 77, N: 78, O: 79, P: 80, Q: 81, R: 82, S: 83, T: 84, U: 85, V: 86, W: 87, X: 88, Y: 89, Z: 90, CONTEXT_MENU: 93, NUM_ZERO: 96, NUM_ONE: 97, NUM_TWO: 98, NUM_THREE: 99, NUM_FOUR: 100, NUM_FIVE: 101, NUM_SIX: 102, NUM_SEVEN: 103, NUM_EIGHT: 104, NUM_NINE: 105, NUM_MULTIPLY: 106, NUM_PLUS: 107, NUM_MINUS: 109, NUM_PERIOD: 110, NUM_DIVISION: 111, F1: 112, F2: 113, F3: 114, F4: 115, F5: 116, F6: 117, F7: 118, F8: 119, F9: 120, F10: 121, F11: 122, F12: 123, WHEEL_SCALE: (function () {
    var a;
    if (Ext.isGecko) {
        a = 3
    } else {
        if (Ext.isMac) {
            if (Ext.isSafari && Ext.webKitVersion >= 532) {
                a = 120
            } else {
                a = 12
            }
            a *= 3
        } else {
            a = 120
        }
    }
    return a
}()), clickRe: /(dbl)?click/, safariKeys: {3: 13, 63234: 37, 63235: 39, 63232: 38, 63233: 40, 63276: 33, 63277: 34, 63272: 46, 63273: 36, 63275: 35}, btnMap: Ext.isIE ? {1: 0, 4: 1, 2: 2} : {0: 0, 1: 1, 2: 2}, constructor: function (a, b) {
    if (a) {
        this.setEvent(a.browserEvent || a, b)
    }
}, setEvent: function (d, e) {
    var c = this, b, a;
    if (d == c || (d && d.browserEvent)) {
        return d
    }
    c.browserEvent = d;
    if (d) {
        b = d.button ? c.btnMap[d.button] : (d.which ? d.which - 1 : -1);
        if (c.clickRe.test(d.type) && b == -1) {
            b = 0
        }
        a = {type: d.type, button: b, shiftKey: d.shiftKey, ctrlKey: d.ctrlKey || d.metaKey || false, altKey: d.altKey, keyCode: d.keyCode, charCode: d.charCode, target: Ext.EventManager.getTarget(d), relatedTarget: Ext.EventManager.getRelatedTarget(d), currentTarget: d.currentTarget, xy: (e ? c.getXY() : null)}
    } else {
        a = {button: -1, shiftKey: false, ctrlKey: false, altKey: false, keyCode: 0, charCode: 0, target: null, xy: [0, 0]}
    }
    Ext.apply(c, a);
    return c
}, stopEvent: function () {
    this.stopPropagation();
    this.preventDefault()
}, preventDefault: function () {
    if (this.browserEvent) {
        Ext.EventManager.preventDefault(this.browserEvent)
    }
}, stopPropagation: function () {
    var a = this.browserEvent;
    if (a) {
        if (a.type == "mousedown") {
            Ext.EventManager.stoppedMouseDownEvent.fire(this)
        }
        Ext.EventManager.stopPropagation(a)
    }
}, getCharCode: function () {
    return this.charCode || this.keyCode
}, getKey: function () {
    return this.normalizeKey(this.keyCode || this.charCode)
}, normalizeKey: function (a) {
    return Ext.isWebKit ? (this.safariKeys[a] || a) : a
}, getPageX: function () {
    return this.getX()
}, getPageY: function () {
    return this.getY()
}, getX: function () {
    return this.getXY()[0]
}, getY: function () {
    return this.getXY()[1]
}, getXY: function () {
    if (!this.xy) {
        this.xy = Ext.EventManager.getPageXY(this.browserEvent)
    }
    return this.xy
}, getTarget: function (b, c, a) {
    if (b) {
        return Ext.fly(this.target).findParent(b, c, a)
    }
    return a ? Ext.get(this.target) : this.target
}, getRelatedTarget: function (b, c, a) {
    if (b) {
        return Ext.fly(this.relatedTarget).findParent(b, c, a)
    }
    return a ? Ext.get(this.relatedTarget) : this.relatedTarget
}, correctWheelDelta: function (c) {
    var b = this.WHEEL_SCALE, a = Math.round(c / b);
    if (!a && c) {
        a = (c < 0) ? -1 : 1
    }
    return a
}, getWheelDeltas: function () {
    var d = this, c = d.browserEvent, b = 0, a = 0;
    if (Ext.isDefined(c.wheelDeltaX)) {
        b = c.wheelDeltaX;
        a = c.wheelDeltaY
    } else {
        if (c.wheelDelta) {
            a = c.wheelDelta
        } else {
            if (c.detail) {
                a = -c.detail;
                if (a > 100) {
                    a = 3
                } else {
                    if (a < -100) {
                        a = -3
                    }
                }
                if (Ext.isDefined(c.axis) && c.axis === c.HORIZONTAL_AXIS) {
                    b = a;
                    a = 0
                }
            }
        }
    }
    return{x: d.correctWheelDelta(b), y: d.correctWheelDelta(a)}
}, getWheelDelta: function () {
    var a = this.getWheelDeltas();
    return a.y
}, within: function (d, e, b) {
    if (d) {
        var c = e ? this.getRelatedTarget() : this.getTarget(), a;
        if (c) {
            a = Ext.fly(d).contains(c);
            if (!a && b) {
                a = c == Ext.getDom(d)
            }
            return a
        }
    }
    return false
}, isNavKeyPress: function () {
    var b = this, a = this.normalizeKey(b.keyCode);
    return(a >= 33 && a <= 40) || a == b.RETURN || a == b.TAB || a == b.ESC
}, isSpecialKey: function () {
    var a = this.normalizeKey(this.keyCode);
    return(this.type == "keypress" && this.ctrlKey) || this.isNavKeyPress() || (a == this.BACKSPACE) || (a >= 16 && a <= 20) || (a >= 44 && a <= 46)
}, getPoint: function () {
    var a = this.getXY();
    return new Ext.util.Point(a[0], a[1])
}, hasModifier: function () {
    return this.ctrlKey || this.altKey || this.shiftKey || this.metaKey
}, injectEvent: (function () {
    var d, e = {}, c;
    if (!Ext.isIE && document.createEvent) {
        d = {createHtmlEvent: function (k, i, h, g) {
            var j = k.createEvent("HTMLEvents");
            j.initEvent(i, h, g);
            return j
        }, createMouseEvent: function (u, s, m, l, o, k, i, j, g, r, q, n, p) {
            var h = u.createEvent("MouseEvents"), t = u.defaultView || window;
            if (h.initMouseEvent) {
                h.initMouseEvent(s, m, l, t, o, k, i, k, i, j, g, r, q, n, p)
            } else {
                h = u.createEvent("UIEvents");
                h.initEvent(s, m, l);
                h.view = t;
                h.detail = o;
                h.screenX = k;
                h.screenY = i;
                h.clientX = k;
                h.clientY = i;
                h.ctrlKey = j;
                h.altKey = g;
                h.metaKey = q;
                h.shiftKey = r;
                h.button = n;
                h.relatedTarget = p
            }
            return h
        }, createUIEvent: function (m, k, i, h, j) {
            var l = m.createEvent("UIEvents"), g = m.defaultView || window;
            l.initUIEvent(k, i, h, g, j);
            return l
        }, fireEvent: function (i, g, h) {
            i.dispatchEvent(h)
        }, fixTarget: function (g) {
            if (g == window && !g.dispatchEvent) {
                return document
            }
            return g
        }}
    } else {
        if (document.createEventObject) {
            c = {0: 1, 1: 4, 2: 2};
            d = {createHtmlEvent: function (k, i, h, g) {
                var j = k.createEventObject();
                j.bubbles = h;
                j.cancelable = g;
                return j
            }, createMouseEvent: function (t, s, m, l, o, k, i, j, g, r, q, n, p) {
                var h = t.createEventObject();
                h.bubbles = m;
                h.cancelable = l;
                h.detail = o;
                h.screenX = k;
                h.screenY = i;
                h.clientX = k;
                h.clientY = i;
                h.ctrlKey = j;
                h.altKey = g;
                h.shiftKey = r;
                h.metaKey = q;
                h.button = c[n] || n;
                h.relatedTarget = p;
                return h
            }, createUIEvent: function (l, j, h, g, i) {
                var k = l.createEventObject();
                k.bubbles = h;
                k.cancelable = g;
                return k
            }, fireEvent: function (i, g, h) {
                i.fireEvent("on" + g, h)
            }, fixTarget: function (g) {
                if (g == document) {
                    return document.documentElement
                }
                return g
            }}
        }
    }
    Ext.Object.each({load: [false, false], unload: [false, false], select: [true, false], change: [true, false], submit: [true, true], reset: [true, false], resize: [true, false], scroll: [true, false]}, function (i, j) {
        var h = j[0], g = j[1];
        e[i] = function (m, k) {
            var l = d.createHtmlEvent(i, h, g);
            d.fireEvent(m, i, l)
        }
    });
    function b(i, h) {
        var g = (i != "mousemove");
        return function (m, j) {
            var l = j.getXY(), k = d.createMouseEvent(m.ownerDocument, i, true, g, h, l[0], l[1], j.ctrlKey, j.altKey, j.shiftKey, j.metaKey, j.button, j.relatedTarget);
            d.fireEvent(m, i, k)
        }
    }

    Ext.each(["click", "dblclick", "mousedown", "mouseup", "mouseover", "mousemove", "mouseout"], function (g) {
        e[g] = b(g, 1)
    });
    Ext.Object.each({focusin: [true, false], focusout: [true, false], activate: [true, true], focus: [false, false], blur: [false, false]}, function (i, j) {
        var h = j[0], g = j[1];
        e[i] = function (m, k) {
            var l = d.createUIEvent(m.ownerDocument, i, h, g, 1);
            d.fireEvent(m, i, l)
        }
    });
    if (!d) {
        e = {};
        d = {fixTarget: function (g) {
            return g
        }}
    }
    function a(h, g) {
    }

    return function (j) {
        var i = this, h = e[i.type] || a, g = j ? (j.dom || j) : i.getTarget();
        g = d.fixTarget(g);
        h(g, i)
    }
}())}, function () {
    Ext.EventObject = new Ext.EventObjectImpl()
});
Ext.define("Ext.dom.AbstractQuery", {select: function (k, b) {
    var h = [], d, g, e, c, a;
    b = b || document;
    if (typeof b == "string") {
        b = document.getElementById(b)
    }
    k = k.split(",");
    for (g = 0, c = k.length; g < c; g++) {
        if (typeof k[g] == "string") {
            if (typeof k[g][0] == "@") {
                d = b.getAttributeNode(k[g].substring(1));
                h.push(d)
            } else {
                d = b.querySelectorAll(k[g]);
                for (e = 0, a = d.length; e < a; e++) {
                    h.push(d[e])
                }
            }
        }
    }
    return h
}, selectNode: function (b, a) {
    return this.select(b, a)[0]
}, is: function (a, b) {
    if (typeof a == "string") {
        a = document.getElementById(a)
    }
    return this.select(b).indexOf(a) !== -1
}});
Ext.define("Ext.dom.AbstractHelper", {emptyTags: /^(?:br|frame|hr|img|input|link|meta|range|spacer|wbr|area|param|col)$/i, confRe: /(?:tag|children|cn|html|tpl|tplData)$/i, endRe: /end/i, attributeTransform: {cls: "class", htmlFor: "for"}, closeTags: {}, decamelizeName: (function () {
    var c = /([a-z])([A-Z])/g, b = {};

    function a(d, g, e) {
        return g + "-" + e.toLowerCase()
    }

    return function (d) {
        return b[d] || (b[d] = d.replace(c, a))
    }
}()), generateMarkup: function (d, c) {
    var h = this, b, j, a, e, g;
    if (typeof d == "string") {
        c.push(d)
    } else {
        if (Ext.isArray(d)) {
            for (e = 0; e < d.length; e++) {
                if (d[e]) {
                    h.generateMarkup(d[e], c)
                }
            }
        } else {
            a = d.tag || "div";
            c.push("<", a);
            for (b in d) {
                if (d.hasOwnProperty(b)) {
                    j = d[b];
                    if (!h.confRe.test(b)) {
                        if (typeof j == "object") {
                            c.push(" ", b, '="');
                            h.generateStyles(j, c).push('"')
                        } else {
                            c.push(" ", h.attributeTransform[b] || b, '="', j, '"')
                        }
                    }
                }
            }
            if (h.emptyTags.test(a)) {
                c.push("/>")
            } else {
                c.push(">");
                if ((j = d.tpl)) {
                    j.applyOut(d.tplData, c)
                }
                if ((j = d.html)) {
                    c.push(j)
                }
                if ((j = d.cn || d.children)) {
                    h.generateMarkup(j, c)
                }
                g = h.closeTags;
                c.push(g[a] || (g[a] = "</" + a + ">"))
            }
        }
    }
    return c
}, generateStyles: function (e, c) {
    var b = c || [], d;
    for (d in e) {
        if (e.hasOwnProperty(d)) {
            b.push(this.decamelizeName(d), ":", e[d], ";")
        }
    }
    return c || b.join("")
}, markup: function (a) {
    if (typeof a == "string") {
        return a
    }
    var b = this.generateMarkup(a, []);
    return b.join("")
}, applyStyles: function (d, e) {
    if (e) {
        var b = 0, a, c;
        d = Ext.fly(d);
        if (typeof e == "function") {
            e = e.call()
        }
        if (typeof e == "string") {
            e = Ext.util.Format.trim(e).split(/\s*(?::|;)\s*/);
            for (a = e.length; b < a;) {
                d.setStyle(e[b++], e[b++])
            }
        } else {
            if (Ext.isObject(e)) {
                d.setStyle(e)
            }
        }
    }
}, insertHtml: function (g, a, h) {
    var e = {}, c, j, i, k, d, b;
    g = g.toLowerCase();
    e.beforebegin = ["BeforeBegin", "previousSibling"];
    e.afterend = ["AfterEnd", "nextSibling"];
    i = a.ownerDocument.createRange();
    j = "setStart" + (this.endRe.test(g) ? "After" : "Before");
    if (e[g]) {
        i[j](a);
        k = i.createContextualFragment(h);
        a.parentNode.insertBefore(k, g == "beforebegin" ? a : a.nextSibling);
        return a[(g == "beforebegin" ? "previous" : "next") + "Sibling"]
    } else {
        d = (g == "afterbegin" ? "first" : "last") + "Child";
        if (a.firstChild) {
            i[j](a[d]);
            k = i.createContextualFragment(h);
            if (g == "afterbegin") {
                a.insertBefore(k, a.firstChild)
            } else {
                a.appendChild(k)
            }
        } else {
            a.innerHTML = h
        }
        return a[d]
    }
    throw'Illegal insertion point -> "' + g + '"'
}, insertBefore: function (a, c, b) {
    return this.doInsert(a, c, b, "beforebegin")
}, insertAfter: function (a, c, b) {
    return this.doInsert(a, c, b, "afterend", "nextSibling")
}, insertFirst: function (a, c, b) {
    return this.doInsert(a, c, b, "afterbegin", "firstChild")
}, append: function (a, c, b) {
    return this.doInsert(a, c, b, "beforeend", "", true)
}, overwrite: function (a, c, b) {
    a = Ext.getDom(a);
    a.innerHTML = this.markup(c);
    return b ? Ext.get(a.firstChild) : a.firstChild
}, doInsert: function (d, g, e, h, c, a) {
    var b = this.insertHtml(h, Ext.getDom(d), this.markup(g));
    return e ? Ext.get(b, true) : b
}});
(function () {
    var a = window.document, b = /^\s+|\s+$/g, c = /\s/;
    if (!Ext.cache) {
        Ext.cache = {}
    }
    Ext.define("Ext.dom.AbstractElement", {inheritableStatics: {get: function (e) {
        var g = this, h = Ext.dom.Element, d, j, i, k;
        if (!e) {
            return null
        }
        if (typeof e == "string") {
            if (e == Ext.windowId) {
                return h.get(window)
            } else {
                if (e == Ext.documentId) {
                    return h.get(a)
                }
            }
            d = Ext.cache[e];
            if (d && d.skipGarbageCollection) {
                j = d.el;
                return j
            }
            if (!(i = a.getElementById(e))) {
                return null
            }
            if (d && d.el) {
                j = Ext.updateCacheEntry(d, i).el
            } else {
                j = new h(i, !!d)
            }
            return j
        } else {
            if (e.tagName) {
                if (!(k = e.id)) {
                    k = Ext.id(e)
                }
                d = Ext.cache[k];
                if (d && d.el) {
                    j = Ext.updateCacheEntry(d, e).el
                } else {
                    j = new h(e, !!d)
                }
                return j
            } else {
                if (e instanceof g) {
                    if (e != g.docEl && e != g.winEl) {
                        k = e.id;
                        d = Ext.cache[k];
                        if (d) {
                            Ext.updateCacheEntry(d, a.getElementById(k) || e.dom)
                        }
                    }
                    return e
                } else {
                    if (e.isComposite) {
                        return e
                    } else {
                        if (Ext.isArray(e)) {
                            return g.select(e)
                        } else {
                            if (e === a) {
                                if (!g.docEl) {
                                    g.docEl = Ext.Object.chain(h.prototype);
                                    g.docEl.dom = a;
                                    g.docEl.id = Ext.id(a);
                                    g.addToCache(g.docEl)
                                }
                                return g.docEl
                            } else {
                                if (e === window) {
                                    if (!g.winEl) {
                                        g.winEl = Ext.Object.chain(h.prototype);
                                        g.winEl.dom = window;
                                        g.winEl.id = Ext.id(window);
                                        g.addToCache(g.winEl)
                                    }
                                    return g.winEl
                                }
                            }
                        }
                    }
                }
            }
        }
        return null
    }, addToCache: function (d, e) {
        if (d) {
            Ext.addCacheEntry(e, d)
        }
        return d
    }, addMethods: function () {
        this.override.apply(this, arguments)
    }, mergeClsList: function () {
        var n, m = {}, k, d, g, l, e, o = [], h = false;
        for (k = 0, d = arguments.length; k < d; k++) {
            n = arguments[k];
            if (Ext.isString(n)) {
                n = n.replace(b, "").split(c)
            }
            if (n) {
                for (g = 0, l = n.length; g < l; g++) {
                    e = n[g];
                    if (!m[e]) {
                        if (k) {
                            h = true
                        }
                        m[e] = true
                    }
                }
            }
        }
        for (e in m) {
            o.push(e)
        }
        o.changed = h;
        return o
    }, removeCls: function (g, l) {
        var e = {}, h, k, j, d = [], m = false;
        if (g) {
            if (Ext.isString(g)) {
                g = g.replace(b, "").split(c)
            }
            for (h = 0, k = g.length; h < k; h++) {
                e[g[h]] = true
            }
        }
        if (l) {
            if (Ext.isString(l)) {
                l = l.split(c)
            }
            for (h = 0, k = l.length; h < k; h++) {
                j = l[h];
                if (e[j]) {
                    m = true;
                    delete e[j]
                }
            }
        }
        for (j in e) {
            d.push(j)
        }
        d.changed = m;
        return d
    }, VISIBILITY: 1, DISPLAY: 2, OFFSETS: 3, ASCLASS: 4}, constructor: function (d, e) {
        var g = this, h = typeof d == "string" ? a.getElementById(d) : d, i;
        if (!h) {
            return null
        }
        i = h.id;
        if (!e && i && Ext.cache[i]) {
            return Ext.cache[i].el
        }
        g.dom = h;
        g.id = i || Ext.id(h);
        g.self.addToCache(g)
    }, set: function (i, e) {
        var g = this.dom, d, h;
        for (d in i) {
            if (i.hasOwnProperty(d)) {
                h = i[d];
                if (d == "style") {
                    this.applyStyles(h)
                } else {
                    if (d == "cls") {
                        g.className = h
                    } else {
                        if (e !== false) {
                            if (h === undefined) {
                                g.removeAttribute(d)
                            } else {
                                g.setAttribute(d, h)
                            }
                        } else {
                            g[d] = h
                        }
                    }
                }
            }
        }
        return this
    }, defaultUnit: "px", is: function (d) {
        return Ext.DomQuery.is(this.dom, d)
    }, getValue: function (d) {
        var e = this.dom.value;
        return d ? parseInt(e, 10) : e
    }, remove: function () {
        var d = this, e = d.dom;
        if (e) {
            Ext.removeNode(e);
            delete d.dom
        }
    }, contains: function (d) {
        if (!d) {
            return false
        }
        var e = this, g = d.dom || d;
        return(g === e.dom) || Ext.dom.AbstractElement.isAncestor(e.dom, g)
    }, getAttribute: function (d, e) {
        var g = this.dom;
        return g.getAttributeNS(e, d) || g.getAttribute(e + ":" + d) || g.getAttribute(d) || g[d]
    }, update: function (d) {
        if (this.dom) {
            this.dom.innerHTML = d
        }
        return this
    }, setHTML: function (d) {
        if (this.dom) {
            this.dom.innerHTML = d
        }
        return this
    }, getHTML: function () {
        return this.dom ? this.dom.innerHTML : ""
    }, hide: function () {
        this.setVisible(false);
        return this
    }, show: function () {
        this.setVisible(true);
        return this
    }, setVisible: function (j, d) {
        var e = this, i = e.self, h = e.getVisibilityMode(), g = Ext.baseCSSPrefix;
        switch (h) {
            case i.VISIBILITY:
                e.removeCls([g + "hidden-display", g + "hidden-offsets"]);
                e[j ? "removeCls" : "addCls"](g + "hidden-visibility");
                break;
            case i.DISPLAY:
                e.removeCls([g + "hidden-visibility", g + "hidden-offsets"]);
                e[j ? "removeCls" : "addCls"](g + "hidden-display");
                break;
            case i.OFFSETS:
                e.removeCls([g + "hidden-visibility", g + "hidden-display"]);
                e[j ? "removeCls" : "addCls"](g + "hidden-offsets");
                break
        }
        return e
    }, getVisibilityMode: function () {
        var e = (this.$cache || this.getCache()).data, d = e.visibilityMode;
        if (d === undefined) {
            e.visibilityMode = d = this.self.DISPLAY
        }
        return d
    }, setVisibilityMode: function (d) {
        (this.$cache || this.getCache()).data.visibilityMode = d;
        return this
    }, getCache: function () {
        var d = this, e = d.dom.id || Ext.id(d.dom);
        d.$cache = Ext.cache[e] || Ext.addCacheEntry(e, null, d.dom);
        return d.$cache
    }}, function () {
        var d = this;
        Ext.getDetachedBody = function () {
            var e = d.detachedBodyEl;
            if (!e) {
                e = a.createElement("div");
                d.detachedBodyEl = e = new d.Fly(e);
                e.isDetachedBody = true
            }
            return e
        };
        Ext.getElementById = function (h) {
            var g = a.getElementById(h), e;
            if (!g && (e = d.detachedBodyEl)) {
                g = e.dom.querySelector("#" + Ext.escapeId(h))
            }
            return g
        };
        Ext.get = function (e) {
            return Ext.dom.Element.get(e)
        };
        this.addStatics({Fly: new Ext.Class({extend: d, isFly: true, constructor: function (e) {
            this.dom = e
        }, attach: function (e) {
            this.dom = e;
            this.$cache = e.id ? Ext.cache[e.id] : null;
            return this
        }}), _flyweights: {}, fly: function (i, g) {
            var h = null, e = d._flyweights;
            g = g || "_global";
            i = Ext.getDom(i);
            if (i) {
                h = e[g] || (e[g] = new d.Fly());
                h.dom = i;
                h.$cache = i.id ? Ext.cache[i.id] : null
            }
            return h
        }});
        Ext.fly = function () {
            return d.fly.apply(d, arguments)
        };
        (function (e) {
            e.destroy = e.remove;
            if (a.querySelector) {
                e.getById = function (i, g) {
                    var h = a.getElementById(i) || this.dom.querySelector("#" + Ext.escapeId(i));
                    return g ? h : (h ? Ext.get(h) : null)
                }
            } else {
                e.getById = function (i, g) {
                    var h = a.getElementById(i);
                    return g ? h : (h ? Ext.get(h) : null)
                }
            }
        }(this.prototype))
    })
}());
Ext.dom.AbstractElement.addInheritableStatics({unitRe: /\d+(px|em|%|en|ex|pt|in|cm|mm|pc)$/i, camelRe: /(-[a-z])/gi, cssRe: /([a-z0-9\-]+)\s*:\s*([^;\s]+(?:\s*[^;\s]+)*);?/gi, opacityRe: /alpha\(opacity=(.*)\)/i, propertyCache: {}, defaultUnit: "px", borders: {l: "border-left-width", r: "border-right-width", t: "border-top-width", b: "border-bottom-width"}, paddings: {l: "padding-left", r: "padding-right", t: "padding-top", b: "padding-bottom"}, margins: {l: "margin-left", r: "margin-right", t: "margin-top", b: "margin-bottom"}, addUnits: function (b, a) {
    if (typeof b == "number") {
        return b + (a || this.defaultUnit || "px")
    }
    if (b === "" || b == "auto" || b === undefined || b === null) {
        return b || ""
    }
    if (!this.unitRe.test(b)) {
        return b || ""
    }
    return b
}, isAncestor: function (b, d) {
    var a = false;
    b = Ext.getDom(b);
    d = Ext.getDom(d);
    if (b && d) {
        if (b.contains) {
            return b.contains(d)
        } else {
            if (b.compareDocumentPosition) {
                return !!(b.compareDocumentPosition(d) & 16)
            } else {
                while ((d = d.parentNode)) {
                    a = d == b || a
                }
            }
        }
    }
    return a
}, parseBox: function (b) {
    if (typeof b != "string") {
        b = b.toString()
    }
    var c = b.split(" "), a = c.length;
    if (a == 1) {
        c[1] = c[2] = c[3] = c[0]
    } else {
        if (a == 2) {
            c[2] = c[0];
            c[3] = c[1]
        } else {
            if (a == 3) {
                c[3] = c[1]
            }
        }
    }
    return{top: parseFloat(c[0]) || 0, right: parseFloat(c[1]) || 0, bottom: parseFloat(c[2]) || 0, left: parseFloat(c[3]) || 0}
}, unitizeBox: function (g, e) {
    var d = this.addUnits, c = this.parseBox(g);
    return d(c.top, e) + " " + d(c.right, e) + " " + d(c.bottom, e) + " " + d(c.left, e)
}, camelReplaceFn: function (b, c) {
    return c.charAt(1).toUpperCase()
}, normalize: function (a) {
    if (a == "float") {
        a = Ext.supports.Float ? "cssFloat" : "styleFloat"
    }
    return this.propertyCache[a] || (this.propertyCache[a] = a.replace(this.camelRe, this.camelReplaceFn))
}, getDocumentHeight: function () {
    return Math.max(!Ext.isStrict ? document.body.scrollHeight : document.documentElement.scrollHeight, this.getViewportHeight())
}, getDocumentWidth: function () {
    return Math.max(!Ext.isStrict ? document.body.scrollWidth : document.documentElement.scrollWidth, this.getViewportWidth())
}, getViewportHeight: function () {
    return window.innerHeight
}, getViewportWidth: function () {
    return window.innerWidth
}, getViewSize: function () {
    return{width: window.innerWidth, height: window.innerHeight}
}, getOrientation: function () {
    if (Ext.supports.OrientationChange) {
        return(window.orientation == 0) ? "portrait" : "landscape"
    }
    return(window.innerHeight > window.innerWidth) ? "portrait" : "landscape"
}, fromPoint: function (a, b) {
    return Ext.get(document.elementFromPoint(a, b))
}, parseStyles: function (c) {
    var a = {}, b = this.cssRe, d;
    if (c) {
        b.lastIndex = 0;
        while ((d = b.exec(c))) {
            a[d[1]] = d[2]
        }
    }
    return a
}});
(function () {
    var g = document, a = Ext.dom.AbstractElement, e = null, d = g.compatMode == "CSS1Compat", c, b = function (i) {
        if (!c) {
            c = new a.Fly()
        }
        c.attach(i);
        return c
    };
    if (!("activeElement" in g) && g.addEventListener) {
        g.addEventListener("focus", function (i) {
            if (i && i.target) {
                e = (i.target == g) ? null : i.target
            }
        }, true)
    }
    function h(j, k, i) {
        return function () {
            j.selectionStart = k;
            j.selectionEnd = i
        }
    }

    a.addInheritableStatics({getActiveElement: function () {
        return g.activeElement || e
    }, getRightMarginFixCleaner: function (n) {
        var k = Ext.supports, l = k.DisplayChangeInputSelectionBug, m = k.DisplayChangeTextAreaSelectionBug, o, i, p, j;
        if (l || m) {
            o = g.activeElement || e;
            i = o && o.tagName;
            if ((m && i == "TEXTAREA") || (l && i == "INPUT" && o.type == "text")) {
                if (Ext.dom.Element.isAncestor(n, o)) {
                    p = o.selectionStart;
                    j = o.selectionEnd;
                    if (Ext.isNumber(p) && Ext.isNumber(j)) {
                        return h(o, p, j)
                    }
                }
            }
        }
        return Ext.emptyFn
    }, getViewWidth: function (i) {
        return i ? Ext.dom.Element.getDocumentWidth() : Ext.dom.Element.getViewportWidth()
    }, getViewHeight: function (i) {
        return i ? Ext.dom.Element.getDocumentHeight() : Ext.dom.Element.getViewportHeight()
    }, getDocumentHeight: function () {
        return Math.max(!d ? g.body.scrollHeight : g.documentElement.scrollHeight, Ext.dom.Element.getViewportHeight())
    }, getDocumentWidth: function () {
        return Math.max(!d ? g.body.scrollWidth : g.documentElement.scrollWidth, Ext.dom.Element.getViewportWidth())
    }, getViewportHeight: function () {
        return Ext.isIE ? (Ext.isStrict ? g.documentElement.clientHeight : g.body.clientHeight) : self.innerHeight
    }, getViewportWidth: function () {
        return(!Ext.isStrict && !Ext.isOpera) ? g.body.clientWidth : Ext.isIE ? g.documentElement.clientWidth : self.innerWidth
    }, getY: function (i) {
        return Ext.dom.Element.getXY(i)[1]
    }, getX: function (i) {
        return Ext.dom.Element.getXY(i)[0]
    }, getXY: function (k) {
        var n = g.body, j = g.documentElement, i = 0, l = 0, o = [0, 0], r = Math.round, m, q;
        k = Ext.getDom(k);
        if (k != g && k != n) {
            if (Ext.isIE) {
                try {
                    m = k.getBoundingClientRect();
                    l = j.clientTop || n.clientTop;
                    i = j.clientLeft || n.clientLeft
                } catch (p) {
                    m = {left: 0, top: 0}
                }
            } else {
                m = k.getBoundingClientRect()
            }
            q = b(document).getScroll();
            o = [r(m.left + q.left - i), r(m.top + q.top - l)]
        }
        return o
    }, setXY: function (j, k) {
        (j = Ext.fly(j, "_setXY")).position();
        var l = j.translatePoints(k), i = j.dom.style, m;
        for (m in l) {
            if (!isNaN(l[m])) {
                i[m] = l[m] + "px"
            }
        }
    }, setX: function (j, i) {
        Ext.dom.Element.setXY(j, [i, false])
    }, setY: function (i, j) {
        Ext.dom.Element.setXY(i, [false, j])
    }, serializeForm: function (k) {
        var l = k.elements || (document.forms[k] || Ext.getDom(k)).elements, v = false, u = encodeURIComponent, p = "", n = l.length, q, i, t, x, w, r, m, s, j;
        for (r = 0; r < n; r++) {
            q = l[r];
            i = q.name;
            t = q.type;
            x = q.options;
            if (!q.disabled && i) {
                if (/select-(one|multiple)/i.test(t)) {
                    s = x.length;
                    for (m = 0; m < s; m++) {
                        j = x[m];
                        if (j.selected) {
                            w = j.hasAttribute ? j.hasAttribute("value") : j.getAttributeNode("value").specified;
                            p += Ext.String.format("{0}={1}&", u(i), u(w ? j.value : j.text))
                        }
                    }
                } else {
                    if (!(/file|undefined|reset|button/i.test(t))) {
                        if (!(/radio|checkbox/i.test(t) && !q.checked) && !(t == "submit" && v)) {
                            p += u(i) + "=" + u(q.value) + "&";
                            v = /submit/i.test(t)
                        }
                    }
                }
            }
        }
        return p.substr(0, p.length - 1)
    }})
}());
Ext.dom.AbstractElement.override({getAnchorXY: function (g, k, n) {
    g = (g || "tl").toLowerCase();
    n = n || {};
    var j = this, a = j.dom == document.body || j.dom == document, b = n.width || a ? window.innerWidth : j.getWidth(), l = n.height || a ? window.innerHeight : j.getHeight(), m, c = Math.round, d = j.getXY(), i = a ? 0 : !k ? d[0] : 0, h = a ? 0 : !k ? d[1] : 0, e = {c: [c(b * 0.5), c(l * 0.5)], t: [c(b * 0.5), 0], l: [0, c(l * 0.5)], r: [b, c(l * 0.5)], b: [c(b * 0.5), l], tl: [0, 0], bl: [0, l], br: [b, l], tr: [b, 0]};
    m = e[g];
    return[m[0] + i, m[1] + h]
}, alignToRe: /^([a-z]+)-([a-z]+)(\?)?$/, getAlignToXY: function (e, z, i, s) {
    s = !!s;
    e = Ext.get(e);
    i = i || [0, 0];
    if (!z || z == "?") {
        z = "tl-bl?"
    } else {
        if (!(/-/).test(z) && z !== "") {
            z = "tl-" + z
        }
    }
    z = z.toLowerCase();
    var v = this, d = z.match(this.alignToRe), n = window.innerWidth, u = window.innerHeight, c = "", b = "", A, w, m, l, q, o, g, a, k, j, r, p, h, t;
    if (!d) {
        throw"Element.alignTo with an invalid alignment " + z
    }
    c = d[1];
    b = d[2];
    t = !!d[3];
    A = v.getAnchorXY(c, true);
    w = e.getAnchorXY(b, s);
    m = w[0] - A[0] + i[0];
    l = w[1] - A[1] + i[1];
    if (t) {
        r = v.getWidth();
        p = v.getHeight();
        h = e.getPageBox();
        a = c.charAt(0);
        g = c.charAt(c.length - 1);
        j = b.charAt(0);
        k = b.charAt(b.length - 1);
        o = ((a == "t" && j == "b") || (a == "b" && j == "t"));
        q = ((g == "r" && k == "l") || (g == "l" && k == "r"));
        if (m + r > n) {
            m = q ? h.left - r : n - r
        }
        if (m < 0) {
            m = q ? h.right : 0
        }
        if (l + p > u) {
            l = o ? h.top - p : u - p
        }
        if (l < 0) {
            l = o ? h.bottom : 0
        }
    }
    return[m, l]
}, getAnchor: function () {
    var b = (this.$cache || this.getCache()).data, a;
    if (!this.dom) {
        return
    }
    a = b._anchor;
    if (!a) {
        a = b._anchor = {}
    }
    return a
}, adjustForConstraints: function (c, b) {
    var a = this.getConstrainVector(b, c);
    if (a) {
        c[0] += a[0];
        c[1] += a[1]
    }
    return c
}});
Ext.dom.AbstractElement.addMethods({appendChild: function (a) {
    return Ext.get(a).appendTo(this)
}, appendTo: function (a) {
    Ext.getDom(a).appendChild(this.dom);
    return this
}, insertBefore: function (a) {
    a = Ext.getDom(a);
    a.parentNode.insertBefore(this.dom, a);
    return this
}, insertAfter: function (a) {
    a = Ext.getDom(a);
    a.parentNode.insertBefore(this.dom, a.nextSibling);
    return this
}, insertFirst: function (b, a) {
    b = b || {};
    if (b.nodeType || b.dom || typeof b == "string") {
        b = Ext.getDom(b);
        this.dom.insertBefore(b, this.dom.firstChild);
        return !a ? Ext.get(b) : b
    } else {
        return this.createChild(b, this.dom.firstChild, a)
    }
}, insertSibling: function (b, g, j) {
    var i = this, k = (g || "before").toLowerCase() == "after", d, a, c, h;
    if (Ext.isArray(b)) {
        a = i;
        c = b.length;
        for (h = 0; h < c; h++) {
            d = Ext.fly(a, "_internal").insertSibling(b[h], g, j);
            if (k) {
                a = d
            }
        }
        return d
    }
    b = b || {};
    if (b.nodeType || b.dom) {
        d = i.dom.parentNode.insertBefore(Ext.getDom(b), k ? i.dom.nextSibling : i.dom);
        if (!j) {
            d = Ext.get(d)
        }
    } else {
        if (k && !i.dom.nextSibling) {
            d = Ext.core.DomHelper.append(i.dom.parentNode, b, !j)
        } else {
            d = Ext.core.DomHelper[k ? "insertAfter" : "insertBefore"](i.dom, b, !j)
        }
    }
    return d
}, replace: function (a) {
    a = Ext.get(a);
    this.insertBefore(a);
    a.remove();
    return this
}, replaceWith: function (a) {
    var b = this;
    if (a.nodeType || a.dom || typeof a == "string") {
        a = Ext.get(a);
        b.dom.parentNode.insertBefore(a, b.dom)
    } else {
        a = Ext.core.DomHelper.insertBefore(b.dom, a)
    }
    delete Ext.cache[b.id];
    Ext.removeNode(b.dom);
    b.id = Ext.id(b.dom = a);
    Ext.dom.AbstractElement.addToCache(b.isFlyweight ? new Ext.dom.AbstractElement(b.dom) : b);
    return b
}, createChild: function (b, a, c) {
    b = b || {tag: "div"};
    if (a) {
        return Ext.core.DomHelper.insertBefore(a, b, c !== true)
    } else {
        return Ext.core.DomHelper[!this.dom.firstChild ? "insertFirst" : "append"](this.dom, b, c !== true)
    }
}, wrap: function (b, c, a) {
    var e = Ext.core.DomHelper.insertBefore(this.dom, b || {tag: "div"}, true), d = e;
    if (a) {
        d = Ext.DomQuery.selectNode(a, e.dom)
    }
    d.appendChild(this.dom);
    return c ? e.dom : e
}, insertHtml: function (b, c, a) {
    var d = Ext.core.DomHelper.insertHtml(b, this.dom, c);
    return a ? Ext.get(d) : d
}});
(function () {
    var a = Ext.dom.AbstractElement;
    a.override({getX: function (b) {
        return this.getXY(b)[0]
    }, getY: function (b) {
        return this.getXY(b)[1]
    }, getXY: function () {
        var b = window.webkitConvertPointFromNodeToPage(this.dom, new WebKitPoint(0, 0));
        return[b.x, b.y]
    }, getOffsetsTo: function (b) {
        var d = this.getXY(), c = Ext.fly(b, "_internal").getXY();
        return[d[0] - c[0], d[1] - c[1]]
    }, setX: function (b) {
        return this.setXY([b, this.getY()])
    }, setY: function (b) {
        return this.setXY([this.getX(), b])
    }, setLeft: function (b) {
        this.setStyle("left", a.addUnits(b));
        return this
    }, setTop: function (b) {
        this.setStyle("top", a.addUnits(b));
        return this
    }, setRight: function (b) {
        this.setStyle("right", a.addUnits(b));
        return this
    }, setBottom: function (b) {
        this.setStyle("bottom", a.addUnits(b));
        return this
    }, setXY: function (g) {
        var c = this, e, b, d;
        if (arguments.length > 1) {
            g = [g, arguments[1]]
        }
        e = c.translatePoints(g);
        b = c.dom.style;
        for (d in e) {
            if (!e.hasOwnProperty(d)) {
                continue
            }
            if (!isNaN(e[d])) {
                b[d] = e[d] + "px"
            }
        }
        return c
    }, getLeft: function (b) {
        return parseInt(this.getStyle("left"), 10) || 0
    }, getRight: function (b) {
        return parseInt(this.getStyle("right"), 10) || 0
    }, getTop: function (b) {
        return parseInt(this.getStyle("top"), 10) || 0
    }, getBottom: function (b) {
        return parseInt(this.getStyle("bottom"), 10) || 0
    }, translatePoints: function (b, i) {
        i = isNaN(b[1]) ? i : b[1];
        b = isNaN(b[0]) ? b : b[0];
        var e = this, g = e.isStyle("position", "relative"), h = e.getXY(), c = parseInt(e.getStyle("left"), 10), d = parseInt(e.getStyle("top"), 10);
        c = !isNaN(c) ? c : (g ? 0 : e.dom.offsetLeft);
        d = !isNaN(d) ? d : (g ? 0 : e.dom.offsetTop);
        return{left: (b - h[0] + c), top: (i - h[1] + d)}
    }, setBox: function (e) {
        var d = this, c = e.width, b = e.height, h = e.top, g = e.left;
        if (g !== undefined) {
            d.setLeft(g)
        }
        if (h !== undefined) {
            d.setTop(h)
        }
        if (c !== undefined) {
            d.setWidth(c)
        }
        if (b !== undefined) {
            d.setHeight(b)
        }
        return this
    }, getBox: function (i, m) {
        var j = this, g = j.dom, d = g.offsetWidth, n = g.offsetHeight, p, h, e, c, o, k;
        if (!m) {
            p = j.getXY()
        } else {
            if (i) {
                p = [0, 0]
            } else {
                p = [parseInt(j.getStyle("left"), 10) || 0, parseInt(j.getStyle("top"), 10) || 0]
            }
        }
        if (!i) {
            h = {x: p[0], y: p[1], 0: p[0], 1: p[1], width: d, height: n}
        } else {
            e = j.getBorderWidth.call(j, "l") + j.getPadding.call(j, "l");
            c = j.getBorderWidth.call(j, "r") + j.getPadding.call(j, "r");
            o = j.getBorderWidth.call(j, "t") + j.getPadding.call(j, "t");
            k = j.getBorderWidth.call(j, "b") + j.getPadding.call(j, "b");
            h = {x: p[0] + e, y: p[1] + o, 0: p[0] + e, 1: p[1] + o, width: d - (e + c), height: n - (o + k)}
        }
        h.left = h.x;
        h.top = h.y;
        h.right = h.x + h.width;
        h.bottom = h.y + h.height;
        return h
    }, getPageBox: function (g) {
        var j = this, d = j.dom, m = d.offsetWidth, i = d.offsetHeight, o = j.getXY(), n = o[1], c = o[0] + m, k = o[1] + i, e = o[0];
        if (!d) {
            return new Ext.util.Region()
        }
        if (g) {
            return new Ext.util.Region(n, c, k, e)
        } else {
            return{left: e, top: n, width: m, height: i, right: c, bottom: k}
        }
    }})
}());
(function () {
    var q = Ext.dom.AbstractElement, o = document.defaultView, n = Ext.Array, m = /^\s+|\s+$/g, b = /\w/g, p = /\s+/, t = /^(?:transparent|(?:rgba[(](?:\s*\d+\s*[,]){3}\s*0\s*[)]))$/i, h = Ext.supports.ClassList, e = "padding", d = "margin", s = "border", k = "-left", r = "-right", l = "-top", c = "-bottom", i = "-width", j = {l: s + k + i, r: s + r + i, t: s + l + i, b: s + c + i}, g = {l: e + k, r: e + r, t: e + l, b: e + c}, a = {l: d + k, r: d + r, t: d + l, b: d + c};
    q.override({styleHooks: {}, addStyles: function (B, A) {
        var w = 0, z = (B || "").match(b), y, u = z.length, x, v = [];
        if (u == 1) {
            w = Math.abs(parseFloat(this.getStyle(A[z[0]])) || 0)
        } else {
            if (u) {
                for (y = 0; y < u; y++) {
                    x = z[y];
                    v.push(A[x])
                }
                v = this.getStyle(v);
                for (y = 0; y < u; y++) {
                    x = z[y];
                    w += Math.abs(parseFloat(v[A[x]]) || 0)
                }
            }
        }
        return w
    }, addCls: h ? function (x) {
        var z = this, B = z.dom, A, y, w, u, v;
        if (typeof(x) == "string") {
            x = x.replace(m, "").split(p)
        }
        if (B && x && !!(u = x.length)) {
            if (!B.className) {
                B.className = x.join(" ")
            } else {
                A = B.classList;
                for (w = 0; w < u; ++w) {
                    v = x[w];
                    if (v) {
                        if (!A.contains(v)) {
                            if (y) {
                                y.push(v)
                            } else {
                                y = B.className.replace(m, "");
                                y = y ? [y, v] : [v]
                            }
                        }
                    }
                }
                if (y) {
                    B.className = y.join(" ")
                }
            }
        }
        return z
    } : function (v) {
        var w = this, y = w.dom, x, u;
        if (y && v && v.length) {
            u = Ext.Element.mergeClsList(y.className, v);
            if (u.changed) {
                y.className = u.join(" ")
            }
        }
        return w
    }, removeCls: function (w) {
        var x = this, y = x.dom, u, v;
        if (typeof(w) == "string") {
            w = w.replace(m, "").split(p)
        }
        if (y && y.className && w && !!(u = w.length)) {
            if (u == 1 && h) {
                if (w[0]) {
                    y.classList.remove(w[0])
                }
            } else {
                v = Ext.Element.removeCls(y.className, w);
                if (v.changed) {
                    y.className = v.join(" ")
                }
            }
        }
        return x
    }, radioCls: function (y) {
        var z = this.dom.parentNode.childNodes, w, x, u;
        y = Ext.isArray(y) ? y : [y];
        for (x = 0, u = z.length; x < u; x++) {
            w = z[x];
            if (w && w.nodeType == 1) {
                Ext.fly(w, "_internal").removeCls(y)
            }
        }
        return this.addCls(y)
    }, toggleCls: h ? function (u) {
        var v = this, w = v.dom;
        if (w) {
            u = u.replace(m, "");
            if (u) {
                w.classList.toggle(u)
            }
        }
        return v
    } : function (u) {
        var v = this;
        return v.hasCls(u) ? v.removeCls(u) : v.addCls(u)
    }, hasCls: h ? function (u) {
        var v = this.dom;
        return(v && u) ? v.classList.contains(u) : false
    } : function (u) {
        var v = this.dom;
        return v ? u && (" " + v.className + " ").indexOf(" " + u + " ") != -1 : false
    }, replaceCls: function (v, u) {
        return this.removeCls(v).addCls(u)
    }, isStyle: function (u, v) {
        return this.getStyle(u) == v
    }, getStyle: function (G, B) {
        var C = this, x = C.dom, J = typeof G != "string", H = C.styleHooks, v = G, D = v, A = 1, z, I, F, E, w, u, y;
        if (J) {
            F = {};
            v = D[0];
            y = 0;
            if (!(A = D.length)) {
                return F
            }
        }
        if (!x || x.documentElement) {
            return F || ""
        }
        z = x.style;
        if (B) {
            u = z
        } else {
            u = x.ownerDocument.defaultView.getComputedStyle(x, null);
            if (!u) {
                B = true;
                u = z
            }
        }
        do {
            E = H[v];
            if (!E) {
                H[v] = E = {name: q.normalize(v)}
            }
            if (E.get) {
                w = E.get(x, C, B, u)
            } else {
                I = E.name;
                w = u[I]
            }
            if (!J) {
                return w
            }
            F[v] = w;
            v = D[++y]
        } while (y < A);
        return F
    }, getStyles: function () {
        var v = Ext.Array.slice(arguments), u = v.length, w;
        if (u && typeof v[u - 1] == "boolean") {
            w = v.pop()
        }
        return this.getStyle(v, w)
    }, isTransparent: function (v) {
        var u = this.getStyle(v);
        return u ? t.test(u) : false
    }, setStyle: function (B, z) {
        var x = this, A = x.dom, u = x.styleHooks, w = A.style, v = B, y;
        if (typeof v == "string") {
            y = u[v];
            if (!y) {
                u[v] = y = {name: q.normalize(v)}
            }
            z = (z == null) ? "" : z;
            if (y.set) {
                y.set(A, z, x)
            } else {
                w[y.name] = z
            }
            if (y.afterSet) {
                y.afterSet(A, z, x)
            }
        } else {
            for (v in B) {
                if (B.hasOwnProperty(v)) {
                    y = u[v];
                    if (!y) {
                        u[v] = y = {name: q.normalize(v)}
                    }
                    z = B[v];
                    z = (z == null) ? "" : z;
                    if (y.set) {
                        y.set(A, z, x)
                    } else {
                        w[y.name] = z
                    }
                    if (y.afterSet) {
                        y.afterSet(A, z, x)
                    }
                }
            }
        }
        return x
    }, getHeight: function (v) {
        var w = this.dom, u = v ? (w.clientHeight - this.getPadding("tb")) : w.offsetHeight;
        return u > 0 ? u : 0
    }, getWidth: function (u) {
        var w = this.dom, v = u ? (w.clientWidth - this.getPadding("lr")) : w.offsetWidth;
        return v > 0 ? v : 0
    }, setWidth: function (u) {
        var v = this;
        v.dom.style.width = q.addUnits(u);
        return v
    }, setHeight: function (u) {
        var v = this;
        v.dom.style.height = q.addUnits(u);
        return v
    }, getBorderWidth: function (u) {
        return this.addStyles(u, j)
    }, getPadding: function (u) {
        return this.addStyles(u, g)
    }, margins: a, applyStyles: function (w) {
        if (w) {
            var v, u, x = this.dom;
            if (typeof w == "function") {
                w = w.call()
            }
            if (typeof w == "string") {
                w = Ext.util.Format.trim(w).split(/\s*(?::|;)\s*/);
                for (v = 0, u = w.length; v < u;) {
                    x.style[q.normalize(w[v++])] = w[v++]
                }
            } else {
                if (typeof w == "object") {
                    this.setStyle(w)
                }
            }
        }
    }, setSize: function (w, u) {
        var x = this, v = x.dom.style;
        if (Ext.isObject(w)) {
            u = w.height;
            w = w.width
        }
        v.width = q.addUnits(w);
        v.height = q.addUnits(u);
        return x
    }, getViewSize: function () {
        var u = document, v = this.dom;
        if (v == u || v == u.body) {
            return{width: q.getViewportWidth(), height: q.getViewportHeight()}
        } else {
            return{width: v.clientWidth, height: v.clientHeight}
        }
    }, getSize: function (v) {
        var u = this.dom;
        return{width: Math.max(0, v ? (u.clientWidth - this.getPadding("lr")) : u.offsetWidth), height: Math.max(0, v ? (u.clientHeight - this.getPadding("tb")) : u.offsetHeight)}
    }, repaint: function () {
        var u = this.dom;
        this.addCls(Ext.baseCSSPrefix + "repaint");
        setTimeout(function () {
            Ext.fly(u).removeCls(Ext.baseCSSPrefix + "repaint")
        }, 1);
        return this
    }, getMargin: function (v) {
        var w = this, y = {t: "top", l: "left", r: "right", b: "bottom"}, u, z, x;
        if (!v) {
            x = [];
            for (u in w.margins) {
                if (w.margins.hasOwnProperty(u)) {
                    x.push(w.margins[u])
                }
            }
            z = w.getStyle(x);
            if (z && typeof z == "object") {
                for (u in w.margins) {
                    if (w.margins.hasOwnProperty(u)) {
                        z[y[u]] = parseFloat(z[w.margins[u]]) || 0
                    }
                }
            }
            return z
        } else {
            return w.addStyles.call(w, v, w.margins)
        }
    }, mask: function (v, z, D) {
        var A = this, w = A.dom, x = (A.$cache || A.getCache()).data, u = x.mask, E, C, B = "", y = Ext.baseCSSPrefix;
        A.addCls(y + "masked");
        if (A.getStyle("position") == "static") {
            A.addCls(y + "masked-relative")
        }
        if (u) {
            u.remove()
        }
        if (z && typeof z == "string") {
            B = " " + z
        } else {
            B = " " + y + "mask-gray"
        }
        E = A.createChild({cls: y + "mask" + ((D !== false) ? "" : (" " + y + "mask-gray")), html: v ? ('<div class="' + (z || (y + "mask-message")) + '">' + v + "</div>") : ""});
        C = A.getSize();
        x.mask = E;
        if (w === document.body) {
            C.height = window.innerHeight;
            if (A.orientationHandler) {
                Ext.EventManager.unOrientationChange(A.orientationHandler, A)
            }
            A.orientationHandler = function () {
                C = A.getSize();
                C.height = window.innerHeight;
                E.setSize(C)
            };
            Ext.EventManager.onOrientationChange(A.orientationHandler, A)
        }
        E.setSize(C);
        if (Ext.is.iPad) {
            Ext.repaint()
        }
    }, unmask: function () {
        var v = this, x = (v.$cache || v.getCache()).data, u = x.mask, w = Ext.baseCSSPrefix;
        if (u) {
            u.remove();
            delete x.mask
        }
        v.removeCls([w + "masked", w + "masked-relative"]);
        if (v.dom === document.body) {
            Ext.EventManager.unOrientationChange(v.orientationHandler, v);
            delete v.orientationHandler
        }
    }});
    q.populateStyleMap = function (B, u) {
        var A = ["margin-", "padding-", "border-width-"], z = ["before", "after"], w, y, v, x;
        for (w = A.length; w--;) {
            for (x = 2; x--;) {
                y = A[w] + z[x];
                B[q.normalize(y)] = B[y] = {name: q.normalize(A[w] + u[x])}
            }
        }
    };
    Ext.onReady(function () {
        var C = Ext.supports, u, A, y, v, B;

        function z(H, E, G, D) {
            var F = D[this.name] || "";
            return t.test(F) ? "transparent" : F
        }

        function x(J, G, I, F) {
            var D = F.marginRight, E, H;
            if (D != "0px") {
                E = J.style;
                H = E.display;
                E.display = "inline-block";
                D = (I ? F : J.ownerDocument.defaultView.getComputedStyle(J, null)).marginRight;
                E.display = H
            }
            return D
        }

        function w(K, H, J, G) {
            var D = G.marginRight, F, E, I;
            if (D != "0px") {
                F = K.style;
                E = q.getRightMarginFixCleaner(K);
                I = F.display;
                F.display = "inline-block";
                D = (J ? G : K.ownerDocument.defaultView.getComputedStyle(K, "")).marginRight;
                F.display = I;
                E()
            }
            return D
        }

        u = q.prototype.styleHooks;
        q.populateStyleMap(u, ["left", "right"]);
        if (C.init) {
            C.init()
        }
        if (!C.RightMargin) {
            u.marginRight = u["margin-right"] = {name: "marginRight", get: (C.DisplayChangeInputSelectionBug || C.DisplayChangeTextAreaSelectionBug) ? w : x}
        }
        if (!C.TransparentColor) {
            A = ["background-color", "border-color", "color", "outline-color"];
            for (y = A.length; y--;) {
                v = A[y];
                B = q.normalize(v);
                u[v] = u[B] = {name: B, get: z}
            }
        }
    })
}());
Ext.dom.AbstractElement.override({findParent: function (h, b, a) {
    var e = this.dom, c = document.documentElement, g = 0, d;
    b = b || 50;
    if (isNaN(b)) {
        d = Ext.getDom(b);
        b = Number.MAX_VALUE
    }
    while (e && e.nodeType == 1 && g < b && e != c && e != d) {
        if (Ext.DomQuery.is(e, h)) {
            return a ? Ext.get(e) : e
        }
        g++;
        e = e.parentNode
    }
    return null
}, findParentNode: function (d, b, a) {
    var c = Ext.fly(this.dom.parentNode, "_internal");
    return c ? c.findParent(d, b, a) : null
}, up: function (b, a) {
    return this.findParentNode(b, a, true)
}, select: function (a, b) {
    return Ext.dom.Element.select(a, this.dom, b)
}, query: function (a) {
    return Ext.DomQuery.select(a, this.dom)
}, down: function (a, b) {
    var c = Ext.DomQuery.selectNode(a, this.dom);
    return b ? c : Ext.get(c)
}, child: function (a, b) {
    var d, c = this, e;
    e = Ext.id(c.dom);
    e = Ext.escapeId(e);
    d = Ext.DomQuery.selectNode("#" + e + " > " + a, c.dom);
    return b ? d : Ext.get(d)
}, parent: function (a, b) {
    return this.matchNode("parentNode", "parentNode", a, b)
}, next: function (a, b) {
    return this.matchNode("nextSibling", "nextSibling", a, b)
}, prev: function (a, b) {
    return this.matchNode("previousSibling", "previousSibling", a, b)
}, first: function (a, b) {
    return this.matchNode("nextSibling", "firstChild", a, b)
}, last: function (a, b) {
    return this.matchNode("previousSibling", "lastChild", a, b)
}, matchNode: function (b, e, a, c) {
    if (!this.dom) {
        return null
    }
    var d = this.dom[e];
    while (d) {
        if (d.nodeType == 1 && (!a || Ext.DomQuery.is(d, a))) {
            return !c ? Ext.get(d) : d
        }
        d = d[b]
    }
    return null
}, isAncestor: function (a) {
    return this.self.isAncestor.call(this.self, this.dom, a)
}});
(function () {
    var b = "afterbegin", i = "afterend", a = "beforebegin", o = "beforeend", l = "<table>", h = "</table>", c = l + "<tbody>", n = "</tbody>" + h, k = c + "<tr>", e = "</tr>" + n, p = document.createElement("div"), m = ["BeforeBegin", "previousSibling"], j = ["AfterEnd", "nextSibling"], d = {beforebegin: m, afterend: j}, g = {beforebegin: m, afterend: j, afterbegin: ["AfterBegin", "firstChild"], beforeend: ["BeforeEnd", "lastChild"]};
    Ext.define("Ext.dom.Helper", {extend: "Ext.dom.AbstractHelper", requires: ["Ext.dom.AbstractElement"], tableRe: /^table|tbody|tr|td$/i, tableElRe: /td|tr|tbody/i, useDom: false, createDom: function (q, w) {
        var r, z = document, u, x, s, y, v, t;
        if (Ext.isArray(q)) {
            r = z.createDocumentFragment();
            for (v = 0, t = q.length; v < t; v++) {
                this.createDom(q[v], r)
            }
        } else {
            if (typeof q == "string") {
                r = z.createTextNode(q)
            } else {
                r = z.createElement(q.tag || "div");
                u = !!r.setAttribute;
                for (x in q) {
                    if (!this.confRe.test(x)) {
                        s = q[x];
                        if (x == "cls") {
                            r.className = s
                        } else {
                            if (u) {
                                r.setAttribute(x, s)
                            } else {
                                r[x] = s
                            }
                        }
                    }
                }
                Ext.DomHelper.applyStyles(r, q.style);
                if ((y = q.children || q.cn)) {
                    this.createDom(y, r)
                } else {
                    if (q.html) {
                        r.innerHTML = q.html
                    }
                }
            }
        }
        if (w) {
            w.appendChild(r)
        }
        return r
    }, ieTable: function (v, q, w, u) {
        p.innerHTML = [q, w, u].join("");
        var r = -1, t = p, s;
        while (++r < v) {
            t = t.firstChild
        }
        s = t.nextSibling;
        if (s) {
            t = document.createDocumentFragment();
            while (s) {
                t.appendChild(s);
                s = s.nextSibling
            }
        }
        return t
    }, insertIntoTable: function (z, s, r, t) {
        var q, w, v = s == a, y = s == b, u = s == o, x = s == i;
        if (z == "td" && (y || u) || !this.tableElRe.test(z) && (v || x)) {
            return null
        }
        w = v ? r : x ? r.nextSibling : y ? r.firstChild : null;
        if (v || x) {
            r = r.parentNode
        }
        if (z == "td" || (z == "tr" && (u || y))) {
            q = this.ieTable(4, k, t, e)
        } else {
            if ((z == "tbody" && (u || y)) || (z == "tr" && (v || x))) {
                q = this.ieTable(3, c, t, n)
            } else {
                q = this.ieTable(2, l, t, h)
            }
        }
        r.insertBefore(q, w);
        return q
    }, createContextualFragment: function (r) {
        var q = document.createDocumentFragment(), s, t;
        p.innerHTML = r;
        t = p.childNodes;
        s = t.length;
        while (s--) {
            q.appendChild(t[0])
        }
        return q
    }, applyStyles: function (q, r) {
        if (r) {
            q = Ext.fly(q);
            if (typeof r == "function") {
                r = r.call()
            }
            if (typeof r == "string") {
                r = Ext.dom.Element.parseStyles(r)
            }
            if (typeof r == "object") {
                q.setStyle(r)
            }
        }
    }, createHtml: function (q) {
        return this.markup(q)
    }, doInsert: function (t, v, u, w, s, q) {
        t = t.dom || Ext.getDom(t);
        var r;
        if (this.useDom) {
            r = this.createDom(v, null);
            if (q) {
                t.appendChild(r)
            } else {
                (s == "firstChild" ? t : t.parentNode).insertBefore(r, t[s] || t)
            }
        } else {
            r = this.insertHtml(w, t, this.markup(v))
        }
        return u ? Ext.get(r, true) : r
    }, overwrite: function (s, r, t) {
        var q;
        s = Ext.getDom(s);
        r = this.markup(r);
        if (Ext.isIE && this.tableRe.test(s.tagName)) {
            while (s.firstChild) {
                s.removeChild(s.firstChild)
            }
            if (r) {
                q = this.insertHtml("afterbegin", s, r);
                return t ? Ext.get(q) : q
            }
            return null
        }
        s.innerHTML = r;
        return t ? Ext.get(s.firstChild) : s.firstChild
    }, insertHtml: function (s, v, t) {
        var x, r, u, q, w;
        s = s.toLowerCase();
        if (v.insertAdjacentHTML) {
            if (Ext.isIE && this.tableRe.test(v.tagName) && (w = this.insertIntoTable(v.tagName.toLowerCase(), s, v, t))) {
                return w
            }
            if ((x = g[s])) {
                v.insertAdjacentHTML(x[0], t);
                return v[x[1]]
            }
        } else {
            if (v.nodeType === 3) {
                s = s === "afterbegin" ? "beforebegin" : s;
                s = s === "beforeend" ? "afterend" : s
            }
            r = Ext.supports.CreateContextualFragment ? v.ownerDocument.createRange() : undefined;
            q = "setStart" + (this.endRe.test(s) ? "After" : "Before");
            if (d[s]) {
                if (r) {
                    r[q](v);
                    w = r.createContextualFragment(t)
                } else {
                    w = this.createContextualFragment(t)
                }
                v.parentNode.insertBefore(w, s == a ? v : v.nextSibling);
                return v[(s == a ? "previous" : "next") + "Sibling"]
            } else {
                u = (s == b ? "first" : "last") + "Child";
                if (v.firstChild) {
                    if (r) {
                        r[q](v[u]);
                        w = r.createContextualFragment(t)
                    } else {
                        w = this.createContextualFragment(t)
                    }
                    if (s == b) {
                        v.insertBefore(w, v.firstChild)
                    } else {
                        v.appendChild(w)
                    }
                } else {
                    v.innerHTML = t
                }
                return v[u]
            }
        }
    }, createTemplate: function (r) {
        var q = this.markup(r);
        return new Ext.Template(q)
    }}, function () {
        Ext.ns("Ext.core");
        Ext.DomHelper = Ext.core.DomHelper = new this
    })
}());
Ext.ns("Ext.core");
Ext.dom.Query = Ext.core.DomQuery = Ext.DomQuery = (function () {
    var cache = {}, simpleCache = {}, valueCache = {}, nonSpace = /\S/, trimRe = /^\s+|\s+$/g, tplRe = /\{(\d+)\}/g, modeRe = /^(\s?[\/>+~]\s?|\s|$)/, tagTokenRe = /^(#)?([\w\-\*\\]+)/, nthRe = /(\d*)n\+?(\d*)/, nthRe2 = /\D/, startIdRe = /^\s*\#/, isIE = window.ActiveXObject ? true : false, key = 30803, longHex = /\\([0-9a-fA-F]{6})/g, shortHex = /\\([0-9a-fA-F]{1,6})\s{0,1}/g, nonHex = /\\([^0-9a-fA-F]{1})/g, escapes = /\\/g, num, hasEscapes, longHexToChar = function ($0, $1) {
        return String.fromCharCode(parseInt($1, 16))
    }, shortToLongHex = function ($0, $1) {
        while ($1.length < 6) {
            $1 = "0" + $1
        }
        return"\\" + $1
    }, charToLongHex = function ($0, $1) {
        num = $1.charCodeAt(0).toString(16);
        if (num.length === 1) {
            num = "0" + num
        }
        return"\\0000" + num
    }, unescapeCssSelector = function (selector) {
        return(hasEscapes) ? selector.replace(longHex, longHexToChar) : selector
    }, setupEscapes = function (path) {
        hasEscapes = (path.indexOf("\\") > -1);
        if (hasEscapes) {
            path = path.replace(shortHex, shortToLongHex).replace(nonHex, charToLongHex).replace(escapes, "\\\\")
        }
        return path
    };
    eval("var batch = 30803;");
    function child(parent, index) {
        var i = 0, n = parent.firstChild;
        while (n) {
            if (n.nodeType == 1) {
                if (++i == index) {
                    return n
                }
            }
            n = n.nextSibling
        }
        return null
    }

    function next(n) {
        while ((n = n.nextSibling) && n.nodeType != 1) {
        }
        return n
    }

    function prev(n) {
        while ((n = n.previousSibling) && n.nodeType != 1) {
        }
        return n
    }

    function children(parent) {
        var n = parent.firstChild, nodeIndex = -1, nextNode;
        while (n) {
            nextNode = n.nextSibling;
            if (n.nodeType == 3 && !nonSpace.test(n.nodeValue)) {
                parent.removeChild(n)
            } else {
                n.nodeIndex = ++nodeIndex
            }
            n = nextNode
        }
        return this
    }

    function byClassName(nodeSet, cls) {
        cls = unescapeCssSelector(cls);
        if (!cls) {
            return nodeSet
        }
        var result = [], ri = -1, i, ci;
        for (i = 0, ci; ci = nodeSet[i]; i++) {
            if ((" " + ci.className + " ").indexOf(cls) != -1) {
                result[++ri] = ci
            }
        }
        return result
    }

    function attrValue(n, attr) {
        if (!n.tagName && typeof n.length != "undefined") {
            n = n[0]
        }
        if (!n) {
            return null
        }
        if (attr == "for") {
            return n.htmlFor
        }
        if (attr == "class" || attr == "className") {
            return n.className
        }
        return n.getAttribute(attr) || n[attr]
    }

    function getNodes(ns, mode, tagName) {
        var result = [], ri = -1, cs, i, ni, j, ci, cn, utag, n, cj;
        if (!ns) {
            return result
        }
        tagName = tagName || "*";
        if (typeof ns.getElementsByTagName != "undefined") {
            ns = [ns]
        }
        if (!mode) {
            for (i = 0, ni; ni = ns[i]; i++) {
                cs = ni.getElementsByTagName(tagName);
                for (j = 0, ci; ci = cs[j]; j++) {
                    result[++ri] = ci
                }
            }
        } else {
            if (mode == "/" || mode == ">") {
                utag = tagName.toUpperCase();
                for (i = 0, ni, cn; ni = ns[i]; i++) {
                    cn = ni.childNodes;
                    for (j = 0, cj; cj = cn[j]; j++) {
                        if (cj.nodeName == utag || cj.nodeName == tagName || tagName == "*") {
                            result[++ri] = cj
                        }
                    }
                }
            } else {
                if (mode == "+") {
                    utag = tagName.toUpperCase();
                    for (i = 0, n; n = ns[i]; i++) {
                        while ((n = n.nextSibling) && n.nodeType != 1) {
                        }
                        if (n && (n.nodeName == utag || n.nodeName == tagName || tagName == "*")) {
                            result[++ri] = n
                        }
                    }
                } else {
                    if (mode == "~") {
                        utag = tagName.toUpperCase();
                        for (i = 0, n; n = ns[i]; i++) {
                            while ((n = n.nextSibling)) {
                                if (n.nodeName == utag || n.nodeName == tagName || tagName == "*") {
                                    result[++ri] = n
                                }
                            }
                        }
                    }
                }
            }
        }
        return result
    }

    function concat(a, b) {
        if (b.slice) {
            return a.concat(b)
        }
        for (var i = 0, l = b.length; i < l; i++) {
            a[a.length] = b[i]
        }
        return a
    }

    function byTag(cs, tagName) {
        if (cs.tagName || cs == document) {
            cs = [cs]
        }
        if (!tagName) {
            return cs
        }
        var result = [], ri = -1, i, ci;
        tagName = tagName.toLowerCase();
        for (i = 0, ci; ci = cs[i]; i++) {
            if (ci.nodeType == 1 && ci.tagName.toLowerCase() == tagName) {
                result[++ri] = ci
            }
        }
        return result
    }

    function byId(cs, id) {
        id = unescapeCssSelector(id);
        if (cs.tagName || cs == document) {
            cs = [cs]
        }
        if (!id) {
            return cs
        }
        var result = [], ri = -1, i, ci;
        for (i = 0, ci; ci = cs[i]; i++) {
            if (ci && ci.id == id) {
                result[++ri] = ci;
                return result
            }
        }
        return result
    }

    function byAttribute(cs, attr, value, op, custom) {
        var result = [], ri = -1, useGetStyle = custom == "{", fn = Ext.DomQuery.operators[op], a, xml, hasXml, i, ci;
        value = unescapeCssSelector(value);
        for (i = 0, ci; ci = cs[i]; i++) {
            if (ci.nodeType != 1) {
                continue
            }
            if (!hasXml) {
                xml = Ext.DomQuery.isXml(ci);
                hasXml = true
            }
            if (!xml) {
                if (useGetStyle) {
                    a = Ext.DomQuery.getStyle(ci, attr)
                } else {
                    if (attr == "class" || attr == "className") {
                        a = ci.className
                    } else {
                        if (attr == "for") {
                            a = ci.htmlFor
                        } else {
                            if (attr == "href") {
                                a = ci.getAttribute("href", 2)
                            } else {
                                a = ci.getAttribute(attr)
                            }
                        }
                    }
                }
            } else {
                a = ci.getAttribute(attr)
            }
            if ((fn && fn(a, value)) || (!fn && a)) {
                result[++ri] = ci
            }
        }
        return result
    }

    function byPseudo(cs, name, value) {
        value = unescapeCssSelector(value);
        return Ext.DomQuery.pseudos[name](cs, value)
    }

    function nodupIEXml(cs) {
        var d = ++key, r, i, len, c;
        cs[0].setAttribute("_nodup", d);
        r = [cs[0]];
        for (i = 1, len = cs.length; i < len; i++) {
            c = cs[i];
            if (!c.getAttribute("_nodup") != d) {
                c.setAttribute("_nodup", d);
                r[r.length] = c
            }
        }
        for (i = 0, len = cs.length; i < len; i++) {
            cs[i].removeAttribute("_nodup")
        }
        return r
    }

    function nodup(cs) {
        if (!cs) {
            return[]
        }
        var len = cs.length, c, i, r = cs, cj, ri = -1, d, j;
        if (!len || typeof cs.nodeType != "undefined" || len == 1) {
            return cs
        }
        if (isIE && typeof cs[0].selectSingleNode != "undefined") {
            return nodupIEXml(cs)
        }
        d = ++key;
        cs[0]._nodup = d;
        for (i = 1; c = cs[i]; i++) {
            if (c._nodup != d) {
                c._nodup = d
            } else {
                r = [];
                for (j = 0; j < i; j++) {
                    r[++ri] = cs[j]
                }
                for (j = i + 1; cj = cs[j]; j++) {
                    if (cj._nodup != d) {
                        cj._nodup = d;
                        r[++ri] = cj
                    }
                }
                return r
            }
        }
        return r
    }

    function quickDiffIEXml(c1, c2) {
        var d = ++key, r = [], i, len;
        for (i = 0, len = c1.length; i < len; i++) {
            c1[i].setAttribute("_qdiff", d)
        }
        for (i = 0, len = c2.length; i < len; i++) {
            if (c2[i].getAttribute("_qdiff") != d) {
                r[r.length] = c2[i]
            }
        }
        for (i = 0, len = c1.length; i < len; i++) {
            c1[i].removeAttribute("_qdiff")
        }
        return r
    }

    function quickDiff(c1, c2) {
        var len1 = c1.length, d = ++key, r = [], i, len;
        if (!len1) {
            return c2
        }
        if (isIE && typeof c1[0].selectSingleNode != "undefined") {
            return quickDiffIEXml(c1, c2)
        }
        for (i = 0; i < len1; i++) {
            c1[i]._qdiff = d
        }
        for (i = 0, len = c2.length; i < len; i++) {
            if (c2[i]._qdiff != d) {
                r[r.length] = c2[i]
            }
        }
        return r
    }

    function quickId(ns, mode, root, id) {
        if (ns == root) {
            id = unescapeCssSelector(id);
            var d = root.ownerDocument || root;
            return d.getElementById(id)
        }
        ns = getNodes(ns, mode, "*");
        return byId(ns, id)
    }

    return{getStyle: function (el, name) {
        return Ext.fly(el).getStyle(name)
    }, compile: function (path, type) {
        type = type || "select";
        var fn = ["var f = function(root){\n var mode; ++batch; var n = root || document;\n"], mode, lastPath, matchers = Ext.DomQuery.matchers, matchersLn = matchers.length, modeMatch, lmode = path.match(modeRe), tokenMatch, matched, j, t, m;
        path = setupEscapes(path);
        if (lmode && lmode[1]) {
            fn[fn.length] = 'mode="' + lmode[1].replace(trimRe, "") + '";';
            path = path.replace(lmode[1], "")
        }
        while (path.substr(0, 1) == "/") {
            path = path.substr(1)
        }
        while (path && lastPath != path) {
            lastPath = path;
            tokenMatch = path.match(tagTokenRe);
            if (type == "select") {
                if (tokenMatch) {
                    if (tokenMatch[1] == "#") {
                        fn[fn.length] = 'n = quickId(n, mode, root, "' + tokenMatch[2] + '");'
                    } else {
                        fn[fn.length] = 'n = getNodes(n, mode, "' + tokenMatch[2] + '");'
                    }
                    path = path.replace(tokenMatch[0], "")
                } else {
                    if (path.substr(0, 1) != "@") {
                        fn[fn.length] = 'n = getNodes(n, mode, "*");'
                    }
                }
            } else {
                if (tokenMatch) {
                    if (tokenMatch[1] == "#") {
                        fn[fn.length] = 'n = byId(n, "' + tokenMatch[2] + '");'
                    } else {
                        fn[fn.length] = 'n = byTag(n, "' + tokenMatch[2] + '");'
                    }
                    path = path.replace(tokenMatch[0], "")
                }
            }
            while (!(modeMatch = path.match(modeRe))) {
                matched = false;
                for (j = 0; j < matchersLn; j++) {
                    t = matchers[j];
                    m = path.match(t.re);
                    if (m) {
                        fn[fn.length] = t.select.replace(tplRe, function (x, i) {
                            return m[i]
                        });
                        path = path.replace(m[0], "");
                        matched = true;
                        break
                    }
                }
                if (!matched) {
                    Ext.Error.raise({sourceClass: "Ext.DomQuery", sourceMethod: "compile", msg: 'Error parsing selector. Parsing failed at "' + path + '"'})
                }
            }
            if (modeMatch[1]) {
                fn[fn.length] = 'mode="' + modeMatch[1].replace(trimRe, "") + '";';
                path = path.replace(modeMatch[1], "")
            }
        }
        fn[fn.length] = "return nodup(n);\n}";
        eval(fn.join(""));
        return f
    }, jsSelect: function (path, root, type) {
        root = root || document;
        if (typeof root == "string") {
            root = document.getElementById(root)
        }
        var paths = path.split(","), results = [], i, len, subPath, result;
        for (i = 0, len = paths.length; i < len; i++) {
            subPath = paths[i].replace(trimRe, "");
            if (!cache[subPath]) {
                cache[subPath] = Ext.DomQuery.compile(subPath, type);
                if (!cache[subPath]) {
                    Ext.Error.raise({sourceClass: "Ext.DomQuery", sourceMethod: "jsSelect", msg: subPath + " is not a valid selector"})
                }
            } else {
                setupEscapes(subPath)
            }
            result = cache[subPath](root);
            if (result && result != document) {
                results = results.concat(result)
            }
        }
        if (paths.length > 1) {
            return nodup(results)
        }
        return results
    }, isXml: function (el) {
        var docEl = (el ? el.ownerDocument || el : 0).documentElement;
        return docEl ? docEl.nodeName !== "HTML" : false
    }, select: document.querySelectorAll ? function (path, root, type) {
        root = root || document;
        if (!Ext.DomQuery.isXml(root)) {
            try {
                if (root.parentNode && (root.nodeType !== 9) && path.indexOf(",") === -1 && !startIdRe.test(path)) {
                    path = "#" + Ext.escapeId(Ext.id(root)) + " " + path;
                    root = root.parentNode
                }
                return Ext.Array.toArray(root.querySelectorAll(path))
            } catch (e) {
            }
        }
        return Ext.DomQuery.jsSelect.call(this, path, root, type)
    } : function (path, root, type) {
        return Ext.DomQuery.jsSelect.call(this, path, root, type)
    }, selectNode: function (path, root) {
        return Ext.DomQuery.select(path, root)[0]
    }, selectValue: function (path, root, defaultValue) {
        path = path.replace(trimRe, "");
        if (!valueCache[path]) {
            valueCache[path] = Ext.DomQuery.compile(path, "select")
        } else {
            setupEscapes(path)
        }
        var n = valueCache[path](root), v;
        n = n[0] ? n[0] : n;
        if (typeof n.normalize == "function") {
            n.normalize()
        }
        v = (n && n.firstChild ? n.firstChild.nodeValue : null);
        return((v === null || v === undefined || v === "") ? defaultValue : v)
    }, selectNumber: function (path, root, defaultValue) {
        var v = Ext.DomQuery.selectValue(path, root, defaultValue || 0);
        return parseFloat(v)
    }, is: function (el, ss) {
        if (typeof el == "string") {
            el = document.getElementById(el)
        }
        var isArray = Ext.isArray(el), result = Ext.DomQuery.filter(isArray ? el : [el], ss);
        return isArray ? (result.length == el.length) : (result.length > 0)
    }, filter: function (els, ss, nonMatches) {
        ss = ss.replace(trimRe, "");
        if (!simpleCache[ss]) {
            simpleCache[ss] = Ext.DomQuery.compile(ss, "simple")
        } else {
            setupEscapes(ss)
        }
        var result = simpleCache[ss](els);
        return nonMatches ? quickDiff(result, els) : result
    }, matchers: [
        {re: /^\.([\w\-\\]+)/, select: 'n = byClassName(n, " {1} ");'},
        {re: /^\:([\w\-]+)(?:\(((?:[^\s>\/]*|.*?))\))?/, select: 'n = byPseudo(n, "{1}", "{2}");'},
        {re: /^(?:([\[\{])(?:@)?([\w\-]+)\s?(?:(=|.=)\s?['"]?(.*?)["']?)?[\]\}])/, select: 'n = byAttribute(n, "{2}", "{4}", "{3}", "{1}");'},
        {re: /^#([\w\-\\]+)/, select: 'n = byId(n, "{1}");'},
        {re: /^@([\w\-]+)/, select: 'return {firstChild:{nodeValue:attrValue(n, "{1}")}};'}
    ], operators: {"=": function (a, v) {
        return a == v
    }, "!=": function (a, v) {
        return a != v
    }, "^=": function (a, v) {
        return a && a.substr(0, v.length) == v
    }, "$=": function (a, v) {
        return a && a.substr(a.length - v.length) == v
    }, "*=": function (a, v) {
        return a && a.indexOf(v) !== -1
    }, "%=": function (a, v) {
        return(a % v) == 0
    }, "|=": function (a, v) {
        return a && (a == v || a.substr(0, v.length + 1) == v + "-")
    }, "~=": function (a, v) {
        return a && (" " + a + " ").indexOf(" " + v + " ") != -1
    }}, pseudos: {"first-child": function (c) {
        var r = [], ri = -1, n, i, ci;
        for (i = 0; (ci = n = c[i]); i++) {
            while ((n = n.previousSibling) && n.nodeType != 1) {
            }
            if (!n) {
                r[++ri] = ci
            }
        }
        return r
    }, "last-child": function (c) {
        var r = [], ri = -1, n, i, ci;
        for (i = 0; (ci = n = c[i]); i++) {
            while ((n = n.nextSibling) && n.nodeType != 1) {
            }
            if (!n) {
                r[++ri] = ci
            }
        }
        return r
    }, "nth-child": function (c, a) {
        var r = [], ri = -1, m = nthRe.exec(a == "even" && "2n" || a == "odd" && "2n+1" || !nthRe2.test(a) && "n+" + a || a), f = (m[1] || 1) - 0, l = m[2] - 0, i, n, j, cn, pn;
        for (i = 0; n = c[i]; i++) {
            pn = n.parentNode;
            if (batch != pn._batch) {
                j = 0;
                for (cn = pn.firstChild; cn; cn = cn.nextSibling) {
                    if (cn.nodeType == 1) {
                        cn.nodeIndex = ++j
                    }
                }
                pn._batch = batch
            }
            if (f == 1) {
                if (l == 0 || n.nodeIndex == l) {
                    r[++ri] = n
                }
            } else {
                if ((n.nodeIndex + l) % f == 0) {
                    r[++ri] = n
                }
            }
        }
        return r
    }, "only-child": function (c) {
        var r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if (!prev(ci) && !next(ci)) {
                r[++ri] = ci
            }
        }
        return r
    }, empty: function (c) {
        var r = [], ri = -1, i, ci, cns, j, cn, empty;
        for (i = 0, ci; ci = c[i]; i++) {
            cns = ci.childNodes;
            j = 0;
            empty = true;
            while (cn = cns[j]) {
                ++j;
                if (cn.nodeType == 1 || cn.nodeType == 3) {
                    empty = false;
                    break
                }
            }
            if (empty) {
                r[++ri] = ci
            }
        }
        return r
    }, contains: function (c, v) {
        var r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if ((ci.textContent || ci.innerText || ci.text || "").indexOf(v) != -1) {
                r[++ri] = ci
            }
        }
        return r
    }, nodeValue: function (c, v) {
        var r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if (ci.firstChild && ci.firstChild.nodeValue == v) {
                r[++ri] = ci
            }
        }
        return r
    }, checked: function (c) {
        var r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if (ci.checked == true) {
                r[++ri] = ci
            }
        }
        return r
    }, not: function (c, ss) {
        return Ext.DomQuery.filter(c, ss, true)
    }, any: function (c, selectors) {
        var ss = selectors.split("|"), r = [], ri = -1, s, i, ci, j;
        for (i = 0; ci = c[i]; i++) {
            for (j = 0; s = ss[j]; j++) {
                if (Ext.DomQuery.is(ci, s)) {
                    r[++ri] = ci;
                    break
                }
            }
        }
        return r
    }, odd: function (c) {
        return this["nth-child"](c, "odd")
    }, even: function (c) {
        return this["nth-child"](c, "even")
    }, nth: function (c, a) {
        return c[a - 1] || []
    }, first: function (c) {
        return c[0] || []
    }, last: function (c) {
        return c[c.length - 1] || []
    }, has: function (c, ss) {
        var s = Ext.DomQuery.select, r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if (s(ss, ci).length > 0) {
                r[++ri] = ci
            }
        }
        return r
    }, next: function (c, ss) {
        var is = Ext.DomQuery.is, r = [], ri = -1, i, ci, n;
        for (i = 0; ci = c[i]; i++) {
            n = next(ci);
            if (n && is(n, ss)) {
                r[++ri] = ci
            }
        }
        return r
    }, prev: function (c, ss) {
        var is = Ext.DomQuery.is, r = [], ri = -1, i, ci, n;
        for (i = 0; ci = c[i]; i++) {
            n = prev(ci);
            if (n && is(n, ss)) {
                r[++ri] = ci
            }
        }
        return r
    }}}
}());
Ext.query = Ext.DomQuery.select;
(function () {
    var HIDDEN = "hidden", DOC = document, VISIBILITY = "visibility", DISPLAY = "display", NONE = "none", XMASKED = Ext.baseCSSPrefix + "masked", XMASKEDRELATIVE = Ext.baseCSSPrefix + "masked-relative", EXTELMASKMSG = Ext.baseCSSPrefix + "mask-msg", bodyRe = /^body/i, visFly, noBoxAdjust = Ext.isStrict ? {select: 1} : {input: 1, select: 1, textarea: 1}, isScrolled = function (c) {
        var r = [], ri = -1, i, ci;
        for (i = 0; ci = c[i]; i++) {
            if (ci.scrollTop > 0 || ci.scrollLeft > 0) {
                r[++ri] = ci
            }
        }
        return r
    }, Element = Ext.define("Ext.dom.Element", {extend: "Ext.dom.AbstractElement", alternateClassName: ["Ext.Element", "Ext.core.Element"], addUnits: function () {
        return this.self.addUnits.apply(this.self, arguments)
    }, focus: function (defer, dom) {
        var me = this, scrollTop, body;
        dom = dom || me.dom;
        body = (dom.ownerDocument || DOC).body || DOC.body;
        try {
            if (Number(defer)) {
                Ext.defer(me.focus, defer, me, [null, dom])
            } else {
                if (dom.offsetHeight > Element.getViewHeight()) {
                    scrollTop = body.scrollTop
                }
                dom.focus();
                if (scrollTop !== undefined) {
                    body.scrollTop = scrollTop
                }
            }
        } catch (e) {
        }
        return me
    }, blur: function () {
        try {
            this.dom.blur()
        } catch (e) {
        }
        return this
    }, isBorderBox: function () {
        var box = Ext.isBorderBox;
        if (box) {
            box = !((this.dom.tagName || "").toLowerCase() in noBoxAdjust)
        }
        return box
    }, hover: function (overFn, outFn, scope, options) {
        var me = this;
        me.on("mouseenter", overFn, scope || me.dom, options);
        me.on("mouseleave", outFn, scope || me.dom, options);
        return me
    }, getAttributeNS: function (ns, name) {
        return this.getAttribute(name, ns)
    }, getAttribute: (Ext.isIE && !(Ext.isIE9 && DOC.documentMode === 9)) ? function (name, ns) {
        var d = this.dom, type;
        if (ns) {
            type = typeof d[ns + ":" + name];
            if (type != "undefined" && type != "unknown") {
                return d[ns + ":" + name] || null
            }
            return null
        }
        if (name === "for") {
            name = "htmlFor"
        }
        return d[name] || null
    } : function (name, ns) {
        var d = this.dom;
        if (ns) {
            return d.getAttributeNS(ns, name) || d.getAttribute(ns + ":" + name)
        }
        return d.getAttribute(name) || d[name] || null
    }, cacheScrollValues: function () {
        var me = this, scrolledDescendants, el, i, scrollValues = [], result = function () {
            for (i = 0; i < scrolledDescendants.length; i++) {
                el = scrolledDescendants[i];
                el.scrollLeft = scrollValues[i][0];
                el.scrollTop = scrollValues[i][1]
            }
        };
        if (!Ext.DomQuery.pseudos.isScrolled) {
            Ext.DomQuery.pseudos.isScrolled = isScrolled
        }
        scrolledDescendants = me.query(":isScrolled");
        for (i = 0; i < scrolledDescendants.length; i++) {
            el = scrolledDescendants[i];
            scrollValues[i] = [el.scrollLeft, el.scrollTop]
        }
        return result
    }, autoBoxAdjust: true, isVisible: function (deep) {
        var me = this, dom = me.dom, stopNode = dom.ownerDocument.documentElement;
        if (!visFly) {
            visFly = new Element.Fly()
        }
        while (dom !== stopNode) {
            if (!dom || dom.nodeType === 11 || (visFly.attach(dom)).isStyle(VISIBILITY, HIDDEN) || visFly.isStyle(DISPLAY, NONE)) {
                return false
            }
            if (!deep) {
                break
            }
            dom = dom.parentNode
        }
        return true
    }, isDisplayed: function () {
        return !this.isStyle(DISPLAY, NONE)
    }, enableDisplayMode: function (display) {
        var me = this;
        me.setVisibilityMode(Element.DISPLAY);
        if (!Ext.isEmpty(display)) {
            (me.$cache || me.getCache()).data.originalDisplay = display
        }
        return me
    }, mask: function (msg, msgCls, elHeight) {
        var me = this, dom = me.dom, setExpression = dom.style.setExpression, data = (me.$cache || me.getCache()).data, maskEl = data.maskEl, maskMsg = data.maskMsg;
        if (!(bodyRe.test(dom.tagName) && me.getStyle("position") == "static")) {
            me.addCls(XMASKEDRELATIVE)
        }
        if (maskEl) {
            maskEl.remove()
        }
        if (maskMsg) {
            maskMsg.remove()
        }
        Ext.DomHelper.append(dom, [
            {cls: Ext.baseCSSPrefix + "mask"},
            {cls: msgCls ? EXTELMASKMSG + " " + msgCls : EXTELMASKMSG, cn: {tag: "div", html: msg || ""}}
        ]);
        maskMsg = Ext.get(dom.lastChild);
        maskEl = Ext.get(maskMsg.dom.previousSibling);
        data.maskMsg = maskMsg;
        data.maskEl = maskEl;
        me.addCls(XMASKED);
        maskEl.setDisplayed(true);
        if (typeof msg == "string") {
            maskMsg.setDisplayed(true);
            maskMsg.center(me)
        } else {
            maskMsg.setDisplayed(false)
        }
        if (!Ext.supports.IncludePaddingInWidthCalculation && setExpression) {
            try {
                maskEl.dom.style.setExpression("width", 'this.parentNode.clientWidth + "px"')
            } catch (e) {
            }
        }
        if (!Ext.supports.IncludePaddingInHeightCalculation && setExpression) {
            try {
                maskEl.dom.style.setExpression("height", "this.parentNode." + (dom == DOC.body ? "scrollHeight" : "offsetHeight") + ' + "px"')
            } catch (e) {
            }
        } else {
            if (Ext.isIE && !(Ext.isIE7 && Ext.isStrict) && me.getStyle("height") == "auto") {
                maskEl.setSize(undefined, elHeight || me.getHeight())
            }
        }
        return maskEl
    }, unmask: function () {
        var me = this, data = (me.$cache || me.getCache()).data, maskEl = data.maskEl, maskMsg = data.maskMsg, style;
        if (maskEl) {
            style = maskEl.dom.style;
            if (style.clearExpression) {
                style.clearExpression("width");
                style.clearExpression("height")
            }
            if (maskEl) {
                maskEl.remove();
                delete data.maskEl
            }
            if (maskMsg) {
                maskMsg.remove();
                delete data.maskMsg
            }
            me.removeCls([XMASKED, XMASKEDRELATIVE])
        }
    }, isMasked: function () {
        var me = this, data = (me.$cache || me.getCache()).data, maskEl = data.maskEl, maskMsg = data.maskMsg, hasMask = false;
        if (maskEl && maskEl.isVisible()) {
            if (maskMsg) {
                maskMsg.center(me)
            }
            hasMask = true
        }
        return hasMask
    }, createShim: function () {
        var el = DOC.createElement("iframe"), shim;
        el.frameBorder = "0";
        el.className = Ext.baseCSSPrefix + "shim";
        el.src = Ext.SSL_SECURE_URL;
        shim = Ext.get(this.dom.parentNode.insertBefore(el, this.dom));
        shim.autoBoxAdjust = false;
        return shim
    }, addKeyListener: function (key, fn, scope) {
        var config;
        if (typeof key != "object" || Ext.isArray(key)) {
            config = {target: this, key: key, fn: fn, scope: scope}
        } else {
            config = {target: this, key: key.key, shift: key.shift, ctrl: key.ctrl, alt: key.alt, fn: fn, scope: scope}
        }
        return new Ext.util.KeyMap(config)
    }, addKeyMap: function (config) {
        return new Ext.util.KeyMap(Ext.apply({target: this}, config))
    }, on: function (eventName, fn, scope, options) {
        Ext.EventManager.on(this, eventName, fn, scope || this, options);
        return this
    }, un: function (eventName, fn, scope) {
        Ext.EventManager.un(this, eventName, fn, scope || this);
        return this
    }, removeAllListeners: function () {
        Ext.EventManager.removeAll(this);
        return this
    }, purgeAllListeners: function () {
        Ext.EventManager.purgeElement(this);
        return this
    }}, function () {
        var EC = Ext.cache, El = this, AbstractElement = Ext.dom.AbstractElement, focusRe = /a|button|embed|iframe|img|input|object|select|textarea/i, nonSpaceRe = /\S/, scriptTagRe = /(?:<script([^>]*)?>)((\n|\r|.)*?)(?:<\/script>)/ig, replaceScriptTagRe = /(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig, srcRe = /\ssrc=([\'\"])(.*?)\1/i, typeRe = /\stype=([\'\"])(.*?)\1/i, useDocForId = !(Ext.isIE6 || Ext.isIE7 || Ext.isIE8);
        El.boxMarkup = '<div class="{0}-tl"><div class="{0}-tr"><div class="{0}-tc"></div></div></div><div class="{0}-ml"><div class="{0}-mr"><div class="{0}-mc"></div></div></div><div class="{0}-bl"><div class="{0}-br"><div class="{0}-bc"></div></div></div>';
        function garbageCollect() {
            if (!Ext.enableGarbageCollector) {
                clearInterval(El.collectorThreadId)
            } else {
                var eid, d, o, t;
                for (eid in EC) {
                    if (!EC.hasOwnProperty(eid)) {
                        continue
                    }
                    o = EC[eid];
                    if (o.skipGarbageCollection) {
                        continue
                    }
                    d = o.dom;
                    if (!d.parentNode || (!d.offsetParent && !Ext.getElementById(eid))) {
                        if (d && Ext.enableListenerCollection) {
                            Ext.EventManager.removeAll(d)
                        }
                        delete EC[eid]
                    }
                }
                if (Ext.isIE) {
                    t = {};
                    for (eid in EC) {
                        if (!EC.hasOwnProperty(eid)) {
                            continue
                        }
                        t[eid] = EC[eid]
                    }
                    EC = Ext.cache = t
                }
            }
        }

        El.collectorThreadId = setInterval(garbageCollect, 30000);
        El.addMethods({monitorMouseLeave: function (delay, handler, scope) {
            var me = this, timer, listeners = {mouseleave: function (e) {
                timer = setTimeout(Ext.Function.bind(handler, scope || me, [e]), delay)
            }, mouseenter: function () {
                clearTimeout(timer)
            }, freezeEvent: true};
            me.on(listeners);
            return listeners
        }, swallowEvent: function (eventName, preventDefault) {
            var me = this, e, eLen;

            function fn(e) {
                e.stopPropagation();
                if (preventDefault) {
                    e.preventDefault()
                }
            }

            if (Ext.isArray(eventName)) {
                eLen = eventName.length;
                for (e = 0; e < eLen; e++) {
                    me.on(eventName[e], fn)
                }
                return me
            }
            me.on(eventName, fn);
            return me
        }, relayEvent: function (eventName, observable) {
            this.on(eventName, function (e) {
                observable.fireEvent(eventName, e)
            })
        }, clean: function (forceReclean) {
            var me = this, dom = me.dom, data = (me.$cache || me.getCache()).data, n = dom.firstChild, ni = -1, nx;
            if (data.isCleaned && forceReclean !== true) {
                return me
            }
            while (n) {
                nx = n.nextSibling;
                if (n.nodeType == 3) {
                    if (!(nonSpaceRe.test(n.nodeValue))) {
                        dom.removeChild(n)
                    } else {
                        if (nx && nx.nodeType == 3) {
                            n.appendData(Ext.String.trim(nx.data));
                            dom.removeChild(nx);
                            nx = n.nextSibling;
                            n.nodeIndex = ++ni
                        }
                    }
                } else {
                    Ext.fly(n).clean();
                    n.nodeIndex = ++ni
                }
                n = nx
            }
            data.isCleaned = true;
            return me
        }, load: function (options) {
            this.getLoader().load(options);
            return this
        }, getLoader: function () {
            var me = this, data = (me.$cache || me.getCache()).data, loader = data.loader;
            if (!loader) {
                data.loader = loader = new Ext.ElementLoader({target: me})
            }
            return loader
        }, syncContent: function (source) {
            source = Ext.getDom(source);
            var me = this, sourceNodes = source.childNodes, sourceLen = sourceNodes.length, dest = me.dom, destNodes = dest.childNodes, destLen = destNodes.length, i, destNode, sourceNode, nodeType;
            dest.style.cssText = source.style.cssText;
            dest.className = source.className;
            if (sourceLen !== destLen) {
                source.innerHTML = dest.innerHTML;
                return
            }
            for (i = 0; i < sourceLen; i++) {
                sourceNode = sourceNodes[i];
                destNode = destNodes[i];
                nodeType = sourceNode.nodeType;
                if (nodeType !== destNode.nodeType || (nodeType === 1 && sourceNode.tagName !== destNode.tagName)) {
                    dest.innerHTML = source.innerHTML;
                    return
                }
                if (nodeType === 3) {
                    destNode.data = sourceNode.data
                } else {
                    if (sourceNode.id && destNode.id !== sourceNode.id) {
                        destNode.id = sourceNode.id
                    }
                    destNode.style.cssText = sourceNode.style.cssText;
                    destNode.className = sourceNode.className;
                    Ext.fly(destNode).syncContent(sourceNode)
                }
            }
        }, update: function (html, loadScripts, callback) {
            var me = this, id, dom, interval;
            if (!me.dom) {
                return me
            }
            html = html || "";
            dom = me.dom;
            if (loadScripts !== true) {
                dom.innerHTML = html;
                Ext.callback(callback, me);
                return me
            }
            id = Ext.id();
            html += '<span id="' + id + '"></span>';
            interval = setInterval(function () {
                var hd, match, attrs, srcMatch, typeMatch, el, s;
                if (!(el = DOC.getElementById(id))) {
                    return false
                }
                clearInterval(interval);
                Ext.removeNode(el);
                hd = Ext.getHead().dom;
                while ((match = scriptTagRe.exec(html))) {
                    attrs = match[1];
                    srcMatch = attrs ? attrs.match(srcRe) : false;
                    if (srcMatch && srcMatch[2]) {
                        s = DOC.createElement("script");
                        s.src = srcMatch[2];
                        typeMatch = attrs.match(typeRe);
                        if (typeMatch && typeMatch[2]) {
                            s.type = typeMatch[2]
                        }
                        hd.appendChild(s)
                    } else {
                        if (match[2] && match[2].length > 0) {
                            if (window.execScript) {
                                window.execScript(match[2])
                            } else {
                                window.eval(match[2])
                            }
                        }
                    }
                }
                Ext.callback(callback, me)
            }, 20);
            dom.innerHTML = html.replace(replaceScriptTagRe, "");
            return me
        }, removeAllListeners: function () {
            this.removeAnchor();
            Ext.EventManager.removeAll(this.dom);
            return this
        }, createProxy: function (config, renderTo, matchBox) {
            config = (typeof config == "object") ? config : {tag: "div", cls: config};
            var me = this, proxy = renderTo ? Ext.DomHelper.append(renderTo, config, true) : Ext.DomHelper.insertBefore(me.dom, config, true);
            proxy.setVisibilityMode(Element.DISPLAY);
            proxy.hide();
            if (matchBox && me.setBox && me.getBox) {
                proxy.setBox(me.getBox())
            }
            return proxy
        }, getScopeParent: function () {
            var parent = this.dom.parentNode;
            if (Ext.scopeResetCSS) {
                parent = parent.parentNode;
                if (!Ext.supports.CSS3LinearGradient || !Ext.supports.CSS3BorderRadius) {
                    parent = parent.parentNode
                }
            }
            return parent
        }, needsTabIndex: function () {
            if (this.dom) {
                if ((this.dom.nodeName === "a") && (!this.dom.href)) {
                    return true
                }
                return !focusRe.test(this.dom.nodeName)
            }
        }, focusable: function () {
            var dom = this.dom, nodeName = dom.nodeName, canFocus = false;
            if (!dom.disabled) {
                if (focusRe.test(nodeName)) {
                    if ((nodeName !== "a") || dom.href) {
                        canFocus = true
                    }
                } else {
                    canFocus = !isNaN(dom.tabIndex)
                }
            }
            return canFocus && this.isVisible(true)
        }});
        if (Ext.isIE) {
            El.prototype.getById = function (id, asDom) {
                var dom = this.dom, cacheItem, el, ret;
                if (dom) {
                    el = (useDocForId && DOC.getElementById(id)) || dom.all[id];
                    if (el) {
                        if (asDom) {
                            ret = el
                        } else {
                            cacheItem = EC[id];
                            if (cacheItem && cacheItem.el) {
                                ret = Ext.updateCacheEntry(cacheItem, el).el
                            } else {
                                ret = new Element(el)
                            }
                        }
                        return ret
                    }
                }
                return asDom ? Ext.getDom(id) : El.get(id)
            }
        }
        El.createAlias({addListener: "on", removeListener: "un", clearListeners: "removeAllListeners"});
        El.Fly = AbstractElement.Fly = new Ext.Class({extend: El, constructor: function (dom) {
            this.dom = dom
        }, attach: AbstractElement.Fly.prototype.attach});
        if (Ext.isIE) {
            Ext.getElementById = function (id) {
                var el = DOC.getElementById(id), detachedBodyEl;
                if (!el && (detachedBodyEl = AbstractElement.detachedBodyEl)) {
                    el = detachedBodyEl.dom.all[id]
                }
                return el
            }
        } else {
            if (!DOC.querySelector) {
                Ext.getDetachedBody = Ext.getBody;
                Ext.getElementById = function (id) {
                    return DOC.getElementById(id)
                }
            }
        }
    })
}());
Ext.dom.Element.override((function () {
    var d = document, c = window, a = /^([a-z]+)-([a-z]+)(\?)?$/, b = Math.round;
    return{getAnchorXY: function (j, o, h) {
        j = (j || "tl").toLowerCase();
        h = h || {};
        var m = this, i = m.dom == d.body || m.dom == d, e = h.width || i ? Ext.dom.Element.getViewWidth() : m.getWidth(), g = h.height || i ? Ext.dom.Element.getViewHeight() : m.getHeight(), q, n = m.getXY(), p = m.getScroll(), l = i ? p.left : !o ? n[0] : 0, k = i ? p.top : !o ? n[1] : 0;
        switch (j) {
            case"tl":
                q = [0, 0];
                break;
            case"bl":
                q = [0, g];
                break;
            case"tr":
                q = [e, 0];
                break;
            case"c":
                q = [b(e * 0.5), b(g * 0.5)];
                break;
            case"t":
                q = [b(e * 0.5), 0];
                break;
            case"l":
                q = [0, b(g * 0.5)];
                break;
            case"r":
                q = [e, b(g * 0.5)];
                break;
            case"b":
                q = [b(e * 0.5), g];
                break;
            case"br":
                q = [e, g]
        }
        return[q[0] + l, q[1] + k]
    }, getAlignToXY: function (m, G, j) {
        m = Ext.get(m);
        if (!m || !m.dom) {
        }
        j = j || [0, 0];
        G = (!G || G == "?" ? "tl-bl?" : (!(/-/).test(G) && G !== "" ? "tl-" + G : G || "tl-bl")).toLowerCase();
        var H = this, l, w, q, o, k, z, A, E = Ext.dom.Element.getViewWidth() - 10, i = Ext.dom.Element.getViewHeight() - 10, g, h, n, p, u, v, F = d.documentElement, s = d.body, D = (F.scrollLeft || s.scrollLeft || 0), B = (F.scrollTop || s.scrollTop || 0), C, t, r, e = G.match(a);
        t = e[1];
        r = e[2];
        C = !!e[3];
        l = H.getAnchorXY(t, true);
        w = m.getAnchorXY(r, false);
        q = w[0] - l[0] + j[0];
        o = w[1] - l[1] + j[1];
        if (C) {
            k = H.getWidth();
            z = H.getHeight();
            A = m.getRegion();
            g = t.charAt(0);
            h = t.charAt(t.length - 1);
            n = r.charAt(0);
            p = r.charAt(r.length - 1);
            u = ((g == "t" && n == "b") || (g == "b" && n == "t"));
            v = ((h == "r" && p == "l") || (h == "l" && p == "r"));
            if (q + k > E + D) {
                q = v ? A.left - k : E + D - k
            }
            if (q < D) {
                q = v ? A.right : D
            }
            if (o + z > i + B) {
                o = u ? A.top - z : i + B - z
            }
            if (o < B) {
                o = u ? A.bottom : B
            }
        }
        return[q, o]
    }, anchorTo: function (g, l, h, e, o, p) {
        var m = this, j = m.dom, n = !Ext.isEmpty(o), i = function () {
            Ext.fly(j).alignTo(g, l, h, e);
            Ext.callback(p, Ext.fly(j))
        }, k = this.getAnchor();
        this.removeAnchor();
        Ext.apply(k, {fn: i, scroll: n});
        Ext.EventManager.onWindowResize(i, null);
        if (n) {
            Ext.EventManager.on(c, "scroll", i, null, {buffer: !isNaN(o) ? o : 50})
        }
        i.call(m);
        return m
    }, removeAnchor: function () {
        var g = this, e = this.getAnchor();
        if (e && e.fn) {
            Ext.EventManager.removeResizeListener(e.fn);
            if (e.scroll) {
                Ext.EventManager.un(c, "scroll", e.fn)
            }
            delete e.fn
        }
        return g
    }, getAlignVector: function (h, g, j) {
        var i = this, e = i.getXY(), k = i.getAlignToXY(h, g, j);
        h = Ext.get(h);
        k[0] -= e[0];
        k[1] -= e[1];
        return k
    }, alignTo: function (h, e, j, g) {
        var i = this;
        return i.setXY(i.getAlignToXY(h, e, j), i.anim && !!g ? i.anim(g) : false)
    }, getConstrainVector: function (i, g) {
        if (!(i instanceof Ext.util.Region)) {
            i = Ext.get(i).getViewRegion()
        }
        var k = this.getRegion(), e = [0, 0], j = (this.shadow && !this.shadowDisabled) ? this.shadow.getShadowSize() : undefined, h = false;
        if (g) {
            k.translateBy(g[0] - k.x, g[1] - k.y)
        }
        if (j) {
            i.adjust(j[0], -j[1], -j[2], j[3])
        }
        if (k.right > i.right) {
            h = true;
            e[0] = (i.right - k.right)
        }
        if (k.left + e[0] < i.left) {
            h = true;
            e[0] = (i.left - k.left)
        }
        if (k.bottom > i.bottom) {
            h = true;
            e[1] = (i.bottom - k.bottom)
        }
        if (k.top + e[1] < i.top) {
            h = true;
            e[1] = (i.top - k.top)
        }
        return h ? e : false
    }, getCenterXY: function () {
        return this.getAlignToXY(d, "c-c")
    }, center: function (e) {
        return this.alignTo(e || d, "c-c")
    }}
}()));
Ext.dom.Element.override({animate: function (b) {
    var d = this, c, e, a = d.dom.id || Ext.id(d.dom);
    if (!Ext.fx.Manager.hasFxBlock(a)) {
        if (b.listeners) {
            c = b.listeners;
            delete b.listeners
        }
        if (b.internalListeners) {
            b.listeners = b.internalListeners;
            delete b.internalListeners
        }
        e = new Ext.fx.Anim(d.anim(b));
        if (c) {
            e.on(c)
        }
        Ext.fx.Manager.queueFx(e)
    }
    return d
}, anim: function (a) {
    if (!Ext.isObject(a)) {
        return(a) ? {} : false
    }
    var b = this, c = a.duration || Ext.fx.Anim.prototype.duration, e = a.easing || "ease", d;
    if (a.stopAnimation) {
        b.stopAnimation()
    }
    Ext.applyIf(a, Ext.fx.Manager.getFxDefaults(b.id));
    Ext.fx.Manager.setFxDefaults(b.id, {delay: 0});
    d = {target: b.dom, remove: a.remove, alternate: a.alternate || false, duration: c, easing: e, callback: a.callback, listeners: a.listeners, iterations: a.iterations || 1, scope: a.scope, block: a.block, concurrent: a.concurrent, delay: a.delay || 0, paused: true, keyframes: a.keyframes, from: a.from || {}, to: Ext.apply({}, a)};
    Ext.apply(d.to, a.to);
    delete d.to.to;
    delete d.to.from;
    delete d.to.remove;
    delete d.to.alternate;
    delete d.to.keyframes;
    delete d.to.iterations;
    delete d.to.listeners;
    delete d.to.target;
    delete d.to.paused;
    delete d.to.callback;
    delete d.to.scope;
    delete d.to.duration;
    delete d.to.easing;
    delete d.to.concurrent;
    delete d.to.block;
    delete d.to.stopAnimation;
    delete d.to.delay;
    return d
}, slideIn: function (c, b, d) {
    var g = this, j = g.dom.style, i, a, e, h;
    c = c || "t";
    b = b || {};
    i = function () {
        var n = this, m = b.listeners, o, k, p, l;
        if (!d) {
            g.fixDisplay()
        }
        o = g.getBox();
        if ((c == "t" || c == "b") && o.height === 0) {
            o.height = g.dom.scrollHeight
        } else {
            if ((c == "l" || c == "r") && o.width === 0) {
                o.width = g.dom.scrollWidth
            }
        }
        k = g.getStyles("width", "height", "left", "right", "top", "bottom", "position", "z-index", true);
        g.setSize(o.width, o.height);
        if (b.preserveScroll) {
            e = g.cacheScrollValues()
        }
        l = g.wrap({id: Ext.id() + "-anim-wrap-for-" + g.id, style: {visibility: d ? "visible" : "hidden"}});
        h = l.dom.parentNode;
        l.setPositioning(g.getPositioning());
        if (l.isStyle("position", "static")) {
            l.position("relative")
        }
        g.clearPositioning("auto");
        l.clip();
        if (e) {
            e()
        }
        g.setStyle({visibility: "", position: "absolute"});
        if (d) {
            l.setSize(o.width, o.height)
        }
        switch (c) {
            case"t":
                p = {from: {width: o.width + "px", height: "0px"}, to: {width: o.width + "px", height: o.height + "px"}};
                j.bottom = "0px";
                break;
            case"l":
                p = {from: {width: "0px", height: o.height + "px"}, to: {width: o.width + "px", height: o.height + "px"}};
                j.right = "0px";
                break;
            case"r":
                p = {from: {x: o.x + o.width, width: "0px", height: o.height + "px"}, to: {x: o.x, width: o.width + "px", height: o.height + "px"}};
                break;
            case"b":
                p = {from: {y: o.y + o.height, width: o.width + "px", height: "0px"}, to: {y: o.y, width: o.width + "px", height: o.height + "px"}};
                break;
            case"tl":
                p = {from: {x: o.x, y: o.y, width: "0px", height: "0px"}, to: {width: o.width + "px", height: o.height + "px"}};
                j.bottom = "0px";
                j.right = "0px";
                break;
            case"bl":
                p = {from: {y: o.y + o.height, width: "0px", height: "0px"}, to: {y: o.y, width: o.width + "px", height: o.height + "px"}};
                j.bottom = "0px";
                break;
            case"br":
                p = {from: {x: o.x + o.width, y: o.y + o.height, width: "0px", height: "0px"}, to: {x: o.x, y: o.y, width: o.width + "px", height: o.height + "px"}};
                break;
            case"tr":
                p = {from: {x: o.x + o.width, width: "0px", height: "0px"}, to: {x: o.x, width: o.width + "px", height: o.height + "px"}};
                j.right = "0px";
                break
        }
        l.show();
        a = Ext.apply({}, b);
        delete a.listeners;
        a = new Ext.fx.Anim(Ext.applyIf(a, {target: l, duration: 500, easing: "ease-out", from: d ? p.to : p.from, to: d ? p.from : p.to}));
        a.on("afteranimate", function () {
            g.setStyle(k);
            if (d) {
                if (b.useDisplay) {
                    g.setDisplayed(false)
                } else {
                    g.hide()
                }
            }
            if (l.dom) {
                if (l.dom.parentNode) {
                    l.dom.parentNode.insertBefore(g.dom, l.dom)
                } else {
                    h.appendChild(g.dom)
                }
                l.remove()
            }
            if (e) {
                e()
            }
            n.end()
        });
        if (m) {
            a.on(m)
        }
    };
    g.animate({duration: b.duration ? Math.max(b.duration, 500) * 2 : 1000, listeners: {beforeanimate: i}});
    return g
}, slideOut: function (a, b) {
    return this.slideIn(a, b, true)
}, puff: function (e) {
    var d = this, b, c = d.getBox(), a = d.getStyles("width", "height", "left", "right", "top", "bottom", "position", "z-index", "font-size", "opacity", true);
    e = Ext.applyIf(e || {}, {easing: "ease-out", duration: 500, useDisplay: false});
    b = function () {
        d.clearOpacity();
        d.show();
        this.to = {width: c.width * 2, height: c.height * 2, x: c.x - (c.width / 2), y: c.y - (c.height / 2), opacity: 0, fontSize: "200%"};
        this.on("afteranimate", function () {
            if (d.dom) {
                if (e.useDisplay) {
                    d.setDisplayed(false)
                } else {
                    d.hide()
                }
                d.setStyle(a);
                e.callback.call(e.scope)
            }
        })
    };
    d.animate({duration: e.duration, easing: e.easing, listeners: {beforeanimate: {fn: b}}});
    return d
}, switchOff: function (c) {
    var b = this, a;
    c = Ext.applyIf(c || {}, {easing: "ease-in", duration: 500, remove: false, useDisplay: false});
    a = function () {
        var h = this, g = b.getSize(), i = b.getXY(), e, d;
        b.clearOpacity();
        b.clip();
        d = b.getPositioning();
        e = new Ext.fx.Animator({target: b, duration: c.duration, easing: c.easing, keyframes: {33: {opacity: 0.3}, 66: {height: 1, y: i[1] + g.height / 2}, 100: {width: 1, x: i[0] + g.width / 2}}});
        e.on("afteranimate", function () {
            if (c.useDisplay) {
                b.setDisplayed(false)
            } else {
                b.hide()
            }
            b.clearOpacity();
            b.setPositioning(d);
            b.setSize(g);
            h.end()
        })
    };
    b.animate({duration: (Math.max(c.duration, 500) * 2), listeners: {beforeanimate: {fn: a}}});
    return b
}, frame: function (a, d, e) {
    var c = this, b;
    a = a || "#C3DAF9";
    d = d || 1;
    e = e || {};
    b = function () {
        c.show();
        var i = this, j = c.getBox(), h = Ext.getBody().createChild({id: c.id + "-anim-proxy", style: {position: "absolute", "pointer-events": "none", "z-index": 35000, border: "0px solid " + a}}), g;
        g = new Ext.fx.Anim({target: h, duration: e.duration || 1000, iterations: d, from: {top: j.y, left: j.x, borderWidth: 0, opacity: 1, height: j.height, width: j.width}, to: {top: j.y - 20, left: j.x - 20, borderWidth: 10, opacity: 0, height: j.height + 40, width: j.width + 40}});
        g.on("afteranimate", function () {
            h.remove();
            i.end()
        })
    };
    c.animate({duration: (Math.max(e.duration, 500) * 2) || 2000, listeners: {beforeanimate: {fn: b}}});
    return c
}, ghost: function (a, d) {
    var c = this, b;
    a = a || "b";
    b = function () {
        var h = c.getWidth(), g = c.getHeight(), i = c.getXY(), e = c.getPositioning(), j = {opacity: 0};
        switch (a) {
            case"t":
                j.y = i[1] - g;
                break;
            case"l":
                j.x = i[0] - h;
                break;
            case"r":
                j.x = i[0] + h;
                break;
            case"b":
                j.y = i[1] + g;
                break;
            case"tl":
                j.x = i[0] - h;
                j.y = i[1] - g;
                break;
            case"bl":
                j.x = i[0] - h;
                j.y = i[1] + g;
                break;
            case"br":
                j.x = i[0] + h;
                j.y = i[1] + g;
                break;
            case"tr":
                j.x = i[0] + h;
                j.y = i[1] - g;
                break
        }
        this.to = j;
        this.on("afteranimate", function () {
            if (c.dom) {
                c.hide();
                c.clearOpacity();
                c.setPositioning(e)
            }
        })
    };
    c.animate(Ext.applyIf(d || {}, {duration: 500, easing: "ease-out", listeners: {beforeanimate: {fn: b}}}));
    return c
}, highlight: function (d, b) {
    var i = this, e = i.dom, k = {}, h, l, g, c, a, j;
    b = b || {};
    c = b.listeners || {};
    g = b.attr || "backgroundColor";
    k[g] = d || "ffff9c";
    if (!b.to) {
        l = {};
        l[g] = b.endColor || i.getColor(g, "ffffff", "")
    } else {
        l = b.to
    }
    b.listeners = Ext.apply(Ext.apply({}, c), {beforeanimate: function () {
        h = e.style[g];
        i.clearOpacity();
        i.show();
        a = c.beforeanimate;
        if (a) {
            j = a.fn || a;
            return j.apply(a.scope || c.scope || window, arguments)
        }
    }, afteranimate: function () {
        if (e) {
            e.style[g] = h
        }
        a = c.afteranimate;
        if (a) {
            j = a.fn || a;
            j.apply(a.scope || c.scope || window, arguments)
        }
    }});
    i.animate(Ext.apply({}, b, {duration: 1000, easing: "ease-in", from: k, to: l}));
    return i
}, pause: function (a) {
    var b = this;
    Ext.fx.Manager.setFxDefaults(b.id, {delay: a});
    return b
}, fadeIn: function (b) {
    var a = this;
    a.animate(Ext.apply({}, b, {opacity: 1, internalListeners: {beforeanimate: function (c) {
        if (a.isStyle("display", "none")) {
            a.setDisplayed("")
        } else {
            a.show()
        }
    }}}));
    return this
}, fadeOut: function (b) {
    var a = this;
    b = Ext.apply({opacity: 0, internalListeners: {afteranimate: function (c) {
        var d = a.dom;
        if (d && c.to.opacity === 0) {
            if (b.useDisplay) {
                a.setDisplayed(false)
            } else {
                a.hide()
            }
        }
    }}}, b);
    a.animate(b);
    return a
}, scale: function (a, b, c) {
    this.animate(Ext.apply({}, c, {width: a, height: b}));
    return this
}, shift: function (a) {
    this.animate(a);
    return this
}});
Ext.dom.Element.override({initDD: function (c, b, d) {
    var a = new Ext.dd.DD(Ext.id(this.dom), c, b);
    return Ext.apply(a, d)
}, initDDProxy: function (c, b, d) {
    var a = new Ext.dd.DDProxy(Ext.id(this.dom), c, b);
    return Ext.apply(a, d)
}, initDDTarget: function (c, b, d) {
    var a = new Ext.dd.DDTarget(Ext.id(this.dom), c, b);
    return Ext.apply(a, d)
}});
(function () {
    var b = Ext.dom.Element, i = "visibility", g = "display", n = "none", e = "hidden", m = "visible", o = "offsets", j = "asclass", a = "nosize", c = "originalDisplay", d = "visibilityMode", h = "isVisible", l = Ext.baseCSSPrefix + "hide-offsets", k = function (q) {
        var r = (q.$cache || q.getCache()).data, s = r[c];
        if (s === undefined) {
            r[c] = s = ""
        }
        return s
    }, p = function (r) {
        var s = (r.$cache || r.getCache()).data, q = s[d];
        if (q === undefined) {
            s[d] = q = b.VISIBILITY
        }
        return q
    };
    b.override({originalDisplay: "", visibilityMode: 1, setVisible: function (u, q) {
        var s = this, t = s.dom, r = p(s);
        if (typeof q == "string") {
            switch (q) {
                case g:
                    r = b.DISPLAY;
                    break;
                case i:
                    r = b.VISIBILITY;
                    break;
                case o:
                    r = b.OFFSETS;
                    break;
                case a:
                case j:
                    r = b.ASCLASS;
                    break
            }
            s.setVisibilityMode(r);
            q = false
        }
        if (!q || !s.anim) {
            if (r == b.DISPLAY) {
                return s.setDisplayed(u)
            } else {
                if (r == b.OFFSETS) {
                    s[u ? "removeCls" : "addCls"](l)
                } else {
                    if (r == b.VISIBILITY) {
                        s.fixDisplay();
                        t.style.visibility = u ? "" : e
                    } else {
                        if (r == b.ASCLASS) {
                            s[u ? "removeCls" : "addCls"](s.visibilityCls || b.visibilityCls)
                        }
                    }
                }
            }
        } else {
            if (u) {
                s.setOpacity(0.01);
                s.setVisible(true)
            }
            if (!Ext.isObject(q)) {
                q = {duration: 350, easing: "ease-in"}
            }
            s.animate(Ext.applyIf({callback: function () {
                if (!u) {
                    s.setVisible(false).setOpacity(1)
                }
            }, to: {opacity: (u) ? 1 : 0}}, q))
        }
        (s.$cache || s.getCache()).data[h] = u;
        return s
    }, hasMetrics: function () {
        var q = p(this);
        return this.isVisible() || (q == b.OFFSETS) || (q == b.VISIBILITY)
    }, toggle: function (q) {
        var r = this;
        r.setVisible(!r.isVisible(), r.anim(q));
        return r
    }, setDisplayed: function (q) {
        if (typeof q == "boolean") {
            q = q ? k(this) : n
        }
        this.setStyle(g, q);
        return this
    }, fixDisplay: function () {
        var q = this;
        if (q.isStyle(g, n)) {
            q.setStyle(i, e);
            q.setStyle(g, k(q));
            if (q.isStyle(g, n)) {
                q.setStyle(g, "block")
            }
        }
    }, hide: function (q) {
        if (typeof q == "string") {
            this.setVisible(false, q);
            return this
        }
        this.setVisible(false, this.anim(q));
        return this
    }, show: function (q) {
        if (typeof q == "string") {
            this.setVisible(true, q);
            return this
        }
        this.setVisible(true, this.anim(q));
        return this
    }})
}());
(function () {
    var r = Ext.dom.Element, n = "left", k = "right", q = "top", h = "bottom", o = "position", j = "static", x = "relative", p = "auto", v = "z-index", u = "BODY", c = "padding", t = "border", s = "-left", m = "-right", a = "-top", l = "-bottom", g = "-width", e = {l: t + s + g, r: t + m + g, t: t + a + g, b: t + l + g}, d = {l: c + s, r: c + m, t: c + a, b: c + l}, w = [d.l, d.r, d.t, d.b], b = [e.l, e.r, e.t, e.b], i = ["position", "top", "left"];
    r.override({getX: function () {
        return r.getX(this.dom)
    }, getY: function () {
        return r.getY(this.dom)
    }, getXY: function () {
        return r.getXY(this.dom)
    }, getOffsetsTo: function (y) {
        var A = this.getXY(), z = Ext.fly(y, "_internal").getXY();
        return[A[0] - z[0], A[1] - z[1]]
    }, setX: function (y, z) {
        return this.setXY([y, this.getY()], z)
    }, setY: function (A, z) {
        return this.setXY([this.getX(), A], z)
    }, setLeft: function (y) {
        this.setStyle(n, this.addUnits(y));
        return this
    }, setTop: function (y) {
        this.setStyle(q, this.addUnits(y));
        return this
    }, setRight: function (y) {
        this.setStyle(k, this.addUnits(y));
        return this
    }, setBottom: function (y) {
        this.setStyle(h, this.addUnits(y));
        return this
    }, setXY: function (A, y) {
        var z = this;
        if (!y || !z.anim) {
            r.setXY(z.dom, A)
        } else {
            if (!Ext.isObject(y)) {
                y = {}
            }
            z.animate(Ext.applyIf({to: {x: A[0], y: A[1]}}, y))
        }
        return z
    }, pxRe: /^\d+(?:\.\d*)?px$/i, getLocalX: function () {
        var A = this, z, y = A.getStyle(n);
        if (!y || y === p) {
            return 0
        }
        if (y && A.pxRe.test(y)) {
            return parseFloat(y)
        }
        y = A.getX();
        z = A.dom.offsetParent;
        if (z) {
            y -= Ext.fly(z).getX()
        }
        return y
    }, getLocalY: function () {
        var A = this, z, B = A.getStyle(q);
        if (!B || B === p) {
            return 0
        }
        if (B && A.pxRe.test(B)) {
            return parseFloat(B)
        }
        B = A.getY();
        z = A.dom.offsetParent;
        if (z) {
            B -= Ext.fly(z).getY()
        }
        return B
    }, getLeft: function (y) {
        return y ? this.getLocalX() : this.getX()
    }, getRight: function (y) {
        return(y ? this.getLocalX() : this.getX()) + this.getWidth()
    }, getTop: function (y) {
        return y ? this.getLocalY() : this.getY()
    }, getBottom: function (y) {
        return(y ? this.getLocalY() : this.getY()) + this.getHeight()
    }, translatePoints: function (z, G) {
        var B = this, A = B.getStyle(i), C = A.position == "relative", F = parseFloat(A.left), E = parseFloat(A.top), D = B.getXY();
        if (Ext.isArray(z)) {
            G = z[1];
            z = z[0]
        }
        if (isNaN(F)) {
            F = C ? 0 : B.dom.offsetLeft
        }
        if (isNaN(E)) {
            E = C ? 0 : B.dom.offsetTop
        }
        F = (typeof z == "number") ? z - D[0] + F : undefined;
        E = (typeof G == "number") ? G - D[1] + E : undefined;
        return{left: F, top: E}
    }, setBox: function (C, D, z) {
        var B = this, y = C.width, A = C.height;
        if ((D && !B.autoBoxAdjust) && !B.isBorderBox()) {
            y -= (B.getBorderWidth("lr") + B.getPadding("lr"));
            A -= (B.getBorderWidth("tb") + B.getPadding("tb"))
        }
        B.setBounds(C.x, C.y, y, A, z);
        return B
    }, getBox: function (D, I) {
        var F = this, M, z, H, C, K, A, y, L, G, J, B, E;
        if (!I) {
            M = F.getXY()
        } else {
            M = F.getStyle([n, q]);
            M = [parseFloat(M.left) || 0, parseFloat(M.top) || 0]
        }
        J = F.getWidth();
        B = F.getHeight();
        if (!D) {
            E = {x: M[0], y: M[1], 0: M[0], 1: M[1], width: J, height: B}
        } else {
            C = F.getStyle(w);
            K = F.getStyle(b);
            A = (parseFloat(K[e.l]) || 0) + (parseFloat(C[d.l]) || 0);
            y = (parseFloat(K[e.r]) || 0) + (parseFloat(C[d.r]) || 0);
            L = (parseFloat(K[e.t]) || 0) + (parseFloat(C[d.t]) || 0);
            G = (parseFloat(K[e.b]) || 0) + (parseFloat(C[d.b]) || 0);
            E = {x: M[0] + A, y: M[1] + L, 0: M[0] + A, 1: M[1] + L, width: J - (A + y), height: B - (L + G)}
        }
        E.right = E.x + E.width;
        E.bottom = E.y + E.height;
        return E
    }, getPageBox: function (B) {
        var D = this, z = D.dom, F = z.nodeName == u, G = F ? Ext.dom.AbstractElement.getViewWidth() : z.offsetWidth, C = F ? Ext.dom.AbstractElement.getViewHeight() : z.offsetHeight, I = D.getXY(), H = I[1], y = I[0] + G, E = I[1] + C, A = I[0];
        if (B) {
            return new Ext.util.Region(H, y, E, A)
        } else {
            return{left: A, top: H, width: G, height: C, right: y, bottom: E}
        }
    }, setLocation: function (z, B, A) {
        return this.setXY([z, B], A)
    }, moveTo: function (z, B, A) {
        return this.setXY([z, B], A)
    }, position: function (D, C, z, B) {
        var A = this;
        if (!D && A.isStyle(o, j)) {
            A.setStyle(o, x)
        } else {
            if (D) {
                A.setStyle(o, D)
            }
        }
        if (C) {
            A.setStyle(v, C)
        }
        if (z || B) {
            A.setXY([z || false, B || false])
        }
    }, clearPositioning: function (y) {
        y = y || "";
        this.setStyle({left: y, right: y, top: y, bottom: y, "z-index": "", position: j});
        return this
    }, getPositioning: function () {
        var y = this.getStyle([n, q, o, k, h, v]);
        y[k] = y[n] ? "" : y[k];
        y[h] = y[q] ? "" : y[h];
        return y
    }, setPositioning: function (y) {
        var A = this, z = A.dom.style;
        A.setStyle(y);
        if (y.right == p) {
            z.right = ""
        }
        if (y.bottom == p) {
            z.bottom = ""
        }
        return A
    }, move: function (H, A, B) {
        var E = this, K = E.getXY(), I = K[0], G = K[1], C = [I - A, G], J = [I + A, G], F = [I, G - A], z = [I, G + A], D = {l: C, left: C, r: J, right: J, t: F, top: F, up: F, b: z, bottom: z, down: z};
        H = H.toLowerCase();
        E.moveTo(D[H][0], D[H][1], B)
    }, setLeftTop: function (A, z) {
        var y = this.dom.style;
        y.left = r.addUnits(A);
        y.top = r.addUnits(z);
        return this
    }, getRegion: function () {
        return this.getPageBox(true)
    }, getViewRegion: function () {
        var C = this, A = C.dom.nodeName == u, z, F, E, D, B, y;
        if (A) {
            z = C.getScroll();
            D = z.left;
            E = z.top;
            B = Ext.dom.AbstractElement.getViewportWidth();
            y = Ext.dom.AbstractElement.getViewportHeight()
        } else {
            F = C.getXY();
            D = F[0] + C.getBorderWidth("l") + C.getPadding("l");
            E = F[1] + C.getBorderWidth("t") + C.getPadding("t");
            B = C.getWidth(true);
            y = C.getHeight(true)
        }
        return new Ext.util.Region(E, D + B - 1, E + y - 1, D)
    }, setBounds: function (A, E, C, z, B) {
        var D = this;
        if (!B || !D.anim) {
            D.setSize(C, z);
            D.setLocation(A, E)
        } else {
            if (!Ext.isObject(B)) {
                B = {}
            }
            D.animate(Ext.applyIf({to: {x: A, y: E, width: D.adjustWidth(C), height: D.adjustHeight(z)}}, B))
        }
        return D
    }, setRegion: function (z, y) {
        return this.setBounds(z.left, z.top, z.right - z.left, z.bottom - z.top, y)
    }})
}());
Ext.dom.Element.override({isScrollable: function () {
    var a = this.dom;
    return a.scrollHeight > a.clientHeight || a.scrollWidth > a.clientWidth
}, getScroll: function () {
    var i = this.dom, h = document, a = h.body, c = h.documentElement, b, g, e;
    if (i == h || i == a) {
        if (Ext.isIE && Ext.isStrict) {
            b = c.scrollLeft;
            g = c.scrollTop
        } else {
            b = window.pageXOffset;
            g = window.pageYOffset
        }
        e = {left: b || (a ? a.scrollLeft : 0), top: g || (a ? a.scrollTop : 0)}
    } else {
        e = {left: i.scrollLeft, top: i.scrollTop}
    }
    return e
}, scrollBy: function (b, a, c) {
    var d = this, e = d.dom;
    if (b.length) {
        c = a;
        a = b[1];
        b = b[0]
    } else {
        if (typeof b != "number") {
            c = a;
            a = b.y;
            b = b.x
        }
    }
    if (b) {
        d.scrollTo("left", Math.max(Math.min(e.scrollLeft + b, e.scrollWidth - e.clientWidth), 0), c)
    }
    if (a) {
        d.scrollTo("top", Math.max(Math.min(e.scrollTop + a, e.scrollHeight - e.clientHeight), 0), c)
    }
    return d
}, scrollTo: function (c, e, a) {
    var g = /top/i.test(c), d = this, h = d.dom, b, i;
    if (!a || !d.anim) {
        i = "scroll" + (g ? "Top" : "Left");
        h[i] = e;
        h[i] = e
    } else {
        b = {to: {}};
        b.to["scroll" + (g ? "Top" : "Left")] = e;
        if (Ext.isObject(a)) {
            Ext.applyIf(b, a)
        }
        d.animate(b)
    }
    return d
}, scrollIntoView: function (b, g, c) {
    b = Ext.getDom(b) || Ext.getBody().dom;
    var d = this.dom, i = this.getOffsetsTo(b), h = i[0] + b.scrollLeft, l = i[1] + b.scrollTop, a = l + d.offsetHeight, m = h + d.offsetWidth, p = b.clientHeight, o = parseInt(b.scrollTop, 10), e = parseInt(b.scrollLeft, 10), j = o + p, n = e + b.clientWidth, k;
    if (d.offsetHeight > p || l < o) {
        k = l
    } else {
        if (a > j) {
            k = a - p
        }
    }
    if (k != null) {
        Ext.get(b).scrollTo("top", k, c)
    }
    if (g !== false) {
        k = null;
        if (d.offsetWidth > b.clientWidth || h < e) {
            k = h
        } else {
            if (m > n) {
                k = m - b.clientWidth
            }
        }
        if (k != null) {
            Ext.get(b).scrollTo("left", k, c)
        }
    }
    return this
}, scrollChildIntoView: function (b, a) {
    Ext.fly(b, "_scrollChildIntoView").scrollIntoView(this, a)
}, scroll: function (m, b, d) {
    if (!this.isScrollable()) {
        return false
    }
    var e = this.dom, g = e.scrollLeft, p = e.scrollTop, n = e.scrollWidth, k = e.scrollHeight, i = e.clientWidth, a = e.clientHeight, c = false, o, j = {l: Math.min(g + b, n - i), r: o = Math.max(g - b, 0), t: Math.max(p - b, 0), b: Math.min(p + b, k - a)};
    j.d = j.b;
    j.u = j.t;
    m = m.substr(0, 1);
    if ((o = j[m]) > -1) {
        c = true;
        this.scrollTo(m == "l" || m == "r" ? "left" : "top", o, this.anim(d))
    }
    return c
}});
(function () {
    var p = Ext.dom.Element, m = document.defaultView, n = /table-row|table-.*-group/, a = "_internal", r = "hidden", o = "height", g = "width", e = "isClipped", i = "overflow", l = "overflow-x", j = "overflow-y", s = "originalClip", b = /#document|body/i, t, d, q, h, u;
    if (!m || !m.getComputedStyle) {
        p.prototype.getStyle = function (z, y) {
            var L = this, G = L.dom, J = typeof z != "string", k = L.styleHooks, w = z, x = w, F = 1, B = y, K, C, v, A, E, H, D;
            if (J) {
                v = {};
                w = x[0];
                D = 0;
                if (!(F = x.length)) {
                    return v
                }
            }
            if (!G || G.documentElement) {
                return v || ""
            }
            C = G.style;
            if (y) {
                H = C
            } else {
                H = G.currentStyle;
                if (!H) {
                    B = true;
                    H = C
                }
            }
            do {
                A = k[w];
                if (!A) {
                    k[w] = A = {name: p.normalize(w)}
                }
                if (A.get) {
                    E = A.get(G, L, B, H)
                } else {
                    K = A.name;
                    if (A.canThrow) {
                        try {
                            E = H[K]
                        } catch (I) {
                            E = ""
                        }
                    } else {
                        E = H ? H[K] : ""
                    }
                }
                if (!J) {
                    return E
                }
                v[w] = E;
                w = x[++D]
            } while (D < F);
            return v
        }
    }
    p.override({getHeight: function (x, v) {
        var w = this, z = w.dom, y = w.isStyle("display", "none"), k, A;
        if (y) {
            return 0
        }
        k = Math.max(z.offsetHeight, z.clientHeight) || 0;
        if (Ext.supports.Direct2DBug) {
            A = w.adjustDirect2DDimension(o);
            if (v) {
                k += A
            } else {
                if (A > 0 && A < 0.5) {
                    k++
                }
            }
        }
        if (x) {
            k -= w.getBorderWidth("tb") + w.getPadding("tb")
        }
        return(k < 0) ? 0 : k
    }, getWidth: function (k, z) {
        var x = this, A = x.dom, y = x.isStyle("display", "none"), w, v, B;
        if (y) {
            return 0
        }
        if (Ext.supports.BoundingClientRect) {
            w = A.getBoundingClientRect();
            v = w.right - w.left;
            v = z ? v : Math.ceil(v)
        } else {
            v = A.offsetWidth
        }
        v = Math.max(v, A.clientWidth) || 0;
        if (Ext.supports.Direct2DBug) {
            B = x.adjustDirect2DDimension(g);
            if (z) {
                v += B
            } else {
                if (B > 0 && B < 0.5) {
                    v++
                }
            }
        }
        if (k) {
            v -= x.getBorderWidth("lr") + x.getPadding("lr")
        }
        return(v < 0) ? 0 : v
    }, setWidth: function (v, k) {
        var w = this;
        v = w.adjustWidth(v);
        if (!k || !w.anim) {
            w.dom.style.width = w.addUnits(v)
        } else {
            if (!Ext.isObject(k)) {
                k = {}
            }
            w.animate(Ext.applyIf({to: {width: v}}, k))
        }
        return w
    }, setHeight: function (k, v) {
        var w = this;
        k = w.adjustHeight(k);
        if (!v || !w.anim) {
            w.dom.style.height = w.addUnits(k)
        } else {
            if (!Ext.isObject(v)) {
                v = {}
            }
            w.animate(Ext.applyIf({to: {height: k}}, v))
        }
        return w
    }, applyStyles: function (k) {
        Ext.DomHelper.applyStyles(this.dom, k);
        return this
    }, setSize: function (w, k, v) {
        var x = this;
        if (Ext.isObject(w)) {
            v = k;
            k = w.height;
            w = w.width
        }
        w = x.adjustWidth(w);
        k = x.adjustHeight(k);
        if (!v || !x.anim) {
            x.dom.style.width = x.addUnits(w);
            x.dom.style.height = x.addUnits(k)
        } else {
            if (v === true) {
                v = {}
            }
            x.animate(Ext.applyIf({to: {width: w, height: k}}, v))
        }
        return x
    }, getViewSize: function () {
        var w = this, x = w.dom, v = b.test(x.nodeName), k;
        if (v) {
            k = {width: p.getViewWidth(), height: p.getViewHeight()}
        } else {
            k = {width: x.clientWidth, height: x.clientHeight}
        }
        return k
    }, getSize: function (k) {
        return{width: this.getWidth(k), height: this.getHeight(k)}
    }, adjustWidth: function (k) {
        var v = this, w = (typeof k == "number");
        if (w && v.autoBoxAdjust && !v.isBorderBox()) {
            k -= (v.getBorderWidth("lr") + v.getPadding("lr"))
        }
        return(w && k < 0) ? 0 : k
    }, adjustHeight: function (k) {
        var v = this, w = (typeof k == "number");
        if (w && v.autoBoxAdjust && !v.isBorderBox()) {
            k -= (v.getBorderWidth("tb") + v.getPadding("tb"))
        }
        return(w && k < 0) ? 0 : k
    }, getColor: function (w, x, C) {
        var z = this.getStyle(w), y = C || C === "" ? C : "#", B, k, A = 0;
        if (!z || (/transparent|inherit/.test(z))) {
            return x
        }
        if (/^r/.test(z)) {
            z = z.slice(4, z.length - 1).split(",");
            k = z.length;
            for (; A < k; A++) {
                B = parseInt(z[A], 10);
                y += (B < 16 ? "0" : "") + B.toString(16)
            }
        } else {
            z = z.replace("#", "");
            y += z.length == 3 ? z.replace(/^(\w)(\w)(\w)$/, "$1$1$2$2$3$3") : z
        }
        return(y.length > 5 ? y.toLowerCase() : x)
    }, setOpacity: function (v, k) {
        var w = this;
        if (!w.dom) {
            return w
        }
        if (!k || !w.anim) {
            w.setStyle("opacity", v)
        } else {
            if (typeof k != "object") {
                k = {duration: 350, easing: "ease-in"}
            }
            w.animate(Ext.applyIf({to: {opacity: v}}, k))
        }
        return w
    }, clearOpacity: function () {
        return this.setOpacity("")
    }, adjustDirect2DDimension: function (w) {
        var B = this, v = B.dom, z = B.getStyle("display"), y = v.style.display, C = v.style.position, A = w === g ? 0 : 1, k = v.currentStyle, x;
        if (z === "inline") {
            v.style.display = "inline-block"
        }
        v.style.position = z.match(n) ? "absolute" : "static";
        x = (parseFloat(k[w]) || parseFloat(k.msTransformOrigin.split(" ")[A]) * 2) % 1;
        v.style.position = C;
        if (z === "inline") {
            v.style.display = y
        }
        return x
    }, clip: function () {
        var v = this, w = (v.$cache || v.getCache()).data, k;
        if (!w[e]) {
            w[e] = true;
            k = v.getStyle([i, l, j]);
            w[s] = {o: k[i], x: k[l], y: k[j]};
            v.setStyle(i, r);
            v.setStyle(l, r);
            v.setStyle(j, r)
        }
        return v
    }, unclip: function () {
        var v = this, w = (v.$cache || v.getCache()).data, k;
        if (w[e]) {
            w[e] = false;
            k = w[s];
            if (k.o) {
                v.setStyle(i, k.o)
            }
            if (k.x) {
                v.setStyle(l, k.x)
            }
            if (k.y) {
                v.setStyle(j, k.y)
            }
        }
        return v
    }, boxWrap: function (k) {
        k = k || Ext.baseCSSPrefix + "box";
        var v = Ext.get(this.insertHtml("beforeBegin", "<div class='" + k + "'>" + Ext.String.format(p.boxMarkup, k) + "</div>"));
        Ext.DomQuery.selectNode("." + k + "-mc", v.dom).appendChild(this.dom);
        return v
    }, getComputedHeight: function () {
        var v = this, k = Math.max(v.dom.offsetHeight, v.dom.clientHeight);
        if (!k) {
            k = parseFloat(v.getStyle(o)) || 0;
            if (!v.isBorderBox()) {
                k += v.getFrameWidth("tb")
            }
        }
        return k
    }, getComputedWidth: function () {
        var v = this, k = Math.max(v.dom.offsetWidth, v.dom.clientWidth);
        if (!k) {
            k = parseFloat(v.getStyle(g)) || 0;
            if (!v.isBorderBox()) {
                k += v.getFrameWidth("lr")
            }
        }
        return k
    }, getFrameWidth: function (v, k) {
        return(k && this.isBorderBox()) ? 0 : (this.getPadding(v) + this.getBorderWidth(v))
    }, addClsOnOver: function (w, z, v) {
        var x = this, y = x.dom, k = Ext.isFunction(z);
        x.hover(function () {
            if (k && z.call(v || x, x) === false) {
                return
            }
            Ext.fly(y, a).addCls(w)
        }, function () {
            Ext.fly(y, a).removeCls(w)
        });
        return x
    }, addClsOnFocus: function (w, z, v) {
        var x = this, y = x.dom, k = Ext.isFunction(z);
        x.on("focus", function () {
            if (k && z.call(v || x, x) === false) {
                return false
            }
            Ext.fly(y, a).addCls(w)
        });
        x.on("blur", function () {
            Ext.fly(y, a).removeCls(w)
        });
        return x
    }, addClsOnClick: function (w, z, v) {
        var x = this, y = x.dom, k = Ext.isFunction(z);
        x.on("mousedown", function () {
            if (k && z.call(v || x, x) === false) {
                return false
            }
            Ext.fly(y, a).addCls(w);
            var B = Ext.getDoc(), A = function () {
                Ext.fly(y, a).removeCls(w);
                B.removeListener("mouseup", A)
            };
            B.on("mouseup", A)
        });
        return x
    }, getStyleSize: function () {
        var z = this, A = this.dom, v = b.test(A.nodeName), y, k, x;
        if (v) {
            return{width: p.getViewWidth(), height: p.getViewHeight()}
        }
        y = z.getStyle([o, g], true);
        if (y.width && y.width != "auto") {
            k = parseFloat(y.width);
            if (z.isBorderBox()) {
                k -= z.getFrameWidth("lr")
            }
        }
        if (y.height && y.height != "auto") {
            x = parseFloat(y.height);
            if (z.isBorderBox()) {
                x -= z.getFrameWidth("tb")
            }
        }
        return{width: k || z.getWidth(true), height: x || z.getHeight(true)}
    }, selectable: function () {
        var k = this;
        k.dom.unselectable = "off";
        k.on("selectstart", function (v) {
            v.stopPropagation();
            return true
        });
        k.applyStyles("-moz-user-select: text; -khtml-user-select: text;");
        k.removeCls(Ext.baseCSSPrefix + "unselectable");
        return k
    }, unselectable: function () {
        var k = this;
        k.dom.unselectable = "on";
        k.swallowEvent("selectstart", true);
        k.applyStyles("-moz-user-select:-moz-none;-khtml-user-select:none;");
        k.addCls(Ext.baseCSSPrefix + "unselectable");
        return k
    }});
    p.prototype.styleHooks = t = Ext.dom.AbstractElement.prototype.styleHooks;
    if (Ext.isIE6 || Ext.isIE7) {
        t.fontSize = t["font-size"] = {name: "fontSize", canThrow: true};
        t.fontStyle = t["font-style"] = {name: "fontStyle", canThrow: true};
        t.fontFamily = t["font-family"] = {name: "fontFamily", canThrow: true}
    }
    if (Ext.isIEQuirks || Ext.isIE && Ext.ieVersion <= 8) {
        function c(x, v, w, k) {
            if (k[this.styleName] == "none") {
                return"0px"
            }
            return k[this.name]
        }

        d = ["Top", "Right", "Bottom", "Left"];
        q = d.length;
        while (q--) {
            h = d[q];
            u = "border" + h + "Width";
            t["border-" + h.toLowerCase() + "-width"] = t[u] = {name: u, styleName: "border" + h + "Style", get: c}
        }
    }
}());
Ext.onReady(function () {
    var c = /alpha\(opacity=(.*)\)/i, b = /^\s+|\s+$/g, a = Ext.dom.Element.prototype.styleHooks;
    a.opacity = {name: "opacity", afterSet: function (g, e, d) {
        if (d.isLayer) {
            d.onOpacitySet(e)
        }
    }};
    if (!Ext.supports.Opacity && Ext.isIE) {
        Ext.apply(a.opacity, {get: function (h) {
            var g = h.style.filter, e, d;
            if (g.match) {
                e = g.match(c);
                if (e) {
                    d = parseFloat(e[1]);
                    if (!isNaN(d)) {
                        return d ? d / 100 : 0
                    }
                }
            }
            return 1
        }, set: function (h, e) {
            var d = h.style, g = d.filter.replace(c, "").replace(b, "");
            d.zoom = 1;
            if (typeof(e) == "number" && e >= 0 && e < 1) {
                e *= 100;
                d.filter = g + (g.length ? " " : "") + "alpha(opacity=" + e + ")"
            } else {
                d.filter = g
            }
        }})
    }
});
Ext.dom.Element.override({select: function (a) {
    return Ext.dom.Element.select(a, false, this.dom)
}});
Ext.define("Ext.dom.CompositeElementLite", {alternateClassName: "Ext.CompositeElementLite", requires: ["Ext.dom.Element", "Ext.dom.Query"], statics: {importElementMethods: function () {
    var b, c = Ext.dom.Element.prototype, a = this.prototype;
    for (b in c) {
        if (typeof c[b] == "function") {
            (function (d) {
                a[d] = a[d] || function () {
                    return this.invoke(d, arguments)
                }
            }).call(a, b)
        }
    }
}}, constructor: function (b, a) {
    this.elements = [];
    this.add(b, a);
    this.el = new Ext.dom.AbstractElement.Fly()
}, isComposite: true, getElement: function (a) {
    return this.el.attach(a)
}, transformElement: function (a) {
    return Ext.getDom(a)
}, getCount: function () {
    return this.elements.length
}, add: function (c, a) {
    var e = this.elements, b, d;
    if (!c) {
        return this
    }
    if (typeof c == "string") {
        c = Ext.dom.Element.selectorFunction(c, a)
    } else {
        if (c.isComposite) {
            c = c.elements
        } else {
            if (!Ext.isIterable(c)) {
                c = [c]
            }
        }
    }
    for (b = 0, d = c.length; b < d; ++b) {
        e.push(this.transformElement(c[b]))
    }
    return this
}, invoke: function (d, a) {
    var g = this.elements, e = g.length, c, b;
    d = Ext.dom.Element.prototype[d];
    for (b = 0; b < e; b++) {
        c = g[b];
        if (c) {
            d.apply(this.getElement(c), a)
        }
    }
    return this
}, item: function (b) {
    var c = this.elements[b], a = null;
    if (c) {
        a = this.getElement(c)
    }
    return a
}, addListener: function (b, j, h, g) {
    var d = this.elements, a = d.length, c, k;
    for (c = 0; c < a; c++) {
        k = d[c];
        if (k) {
            Ext.EventManager.on(k, b, j, h || k, g)
        }
    }
    return this
}, each: function (g, d) {
    var h = this, c = h.elements, a = c.length, b, j;
    for (b = 0; b < a; b++) {
        j = c[b];
        if (j) {
            j = this.getElement(j);
            if (g.call(d || j, j, h, b) === false) {
                break
            }
        }
    }
    return h
}, fill: function (a) {
    var b = this;
    b.elements = [];
    b.add(a);
    return b
}, filter: function (b) {
    var h = this, c = h.elements, g = c.length, d = [], e = 0, j = typeof b == "function", k, a;
    for (; e < g; e++) {
        a = c[e];
        k = false;
        if (a) {
            a = h.getElement(a);
            if (j) {
                k = b.call(a, a, h, e) !== false
            } else {
                k = a.is(b)
            }
            if (k) {
                d.push(h.transformElement(a))
            }
        }
    }
    h.elements = d;
    return h
}, indexOf: function (a) {
    return Ext.Array.indexOf(this.elements, this.transformElement(a))
}, replaceElement: function (e, c, a) {
    var b = !isNaN(e) ? e : this.indexOf(e), g;
    if (b > -1) {
        c = Ext.getDom(c);
        if (a) {
            g = this.elements[b];
            g.parentNode.insertBefore(c, g);
            Ext.removeNode(g)
        }
        Ext.Array.splice(this.elements, b, 1, c)
    }
    return this
}, clear: function () {
    this.elements = []
}, addElements: function (d, b) {
    if (!d) {
        return this
    }
    if (typeof d == "string") {
        d = Ext.dom.Element.selectorFunction(d, b)
    }
    var c = this.elements, a = d.length, g;
    for (g = 0; g < a; g++) {
        c.push(Ext.get(d[g]))
    }
    return this
}, first: function () {
    return this.item(0)
}, last: function () {
    return this.item(this.getCount() - 1)
}, contains: function (a) {
    return this.indexOf(a) != -1
}, removeElement: function (e, i) {
    e = [].concat(e);
    var d = this, g = d.elements, c = e.length, h, b, a;
    for (a = 0; a < c; a++) {
        h = e[a];
        if ((b = (g[h] || g[h = d.indexOf(h)]))) {
            if (i) {
                if (b.dom) {
                    b.remove()
                } else {
                    Ext.removeNode(b)
                }
            }
            Ext.Array.erase(g, h, 1)
        }
    }
    return d
}}, function () {
    this.importElementMethods();
    this.prototype.on = this.prototype.addListener;
    if (Ext.DomQuery) {
        Ext.dom.Element.selectorFunction = Ext.DomQuery.select
    }
    Ext.dom.Element.select = function (a, b) {
        var c;
        if (typeof a == "string") {
            c = Ext.dom.Element.selectorFunction(a, b)
        } else {
            if (a.length !== undefined) {
                c = a
            } else {
            }
        }
        return new Ext.CompositeElementLite(c)
    };
    Ext.select = function () {
        return Ext.dom.Element.select.apply(Ext.dom.Element, arguments)
    }
});
Ext.define("Ext.dom.CompositeElement", {alternateClassName: "Ext.CompositeElement", extend: "Ext.dom.CompositeElementLite", getElement: function (a) {
    return a
}, transformElement: function (a) {
    return Ext.get(a)
}}, function () {
    Ext.dom.Element.select = function (a, d, b) {
        var c;
        if (typeof a == "string") {
            c = Ext.dom.Element.selectorFunction(a, b)
        } else {
            if (a.length !== undefined) {
                c = a
            } else {
            }
        }
        return(d === true) ? new Ext.CompositeElement(c) : new Ext.CompositeElementLite(c)
    }
});
Ext.select = Ext.Element.select;
Ext.ClassManager.addNameAlternateMappings({"Ext.draw.engine.ImageExporter": [], "Ext.layout.component.Auto": [], "Ext.grid.property.Store": ["Ext.grid.PropertyStore"], "Ext.layout.container.Box": ["Ext.layout.BoxLayout"], "Ext.direct.JsonProvider": [], "Ext.tree.Panel": ["Ext.tree.TreePanel", "Ext.TreePanel"], "Ext.data.Model": ["Ext.data.Record"], "Ext.data.reader.Reader": ["Ext.data.Reader", "Ext.data.DataReader"], "Ext.tab.Tab": [], "Ext.button.Button": ["Ext.Button"], "Ext.util.Grouper": [], "Ext.util.TaskRunner": [], "Ext.direct.RemotingProvider": [], "Ext.data.NodeInterface": [], "Ext.grid.column.Date": ["Ext.grid.DateColumn"], "Ext.form.field.Trigger": ["Ext.form.TriggerField", "Ext.form.TwinTriggerField", "Ext.form.Trigger"], "Ext.grid.plugin.RowEditing": [], "Ext.tip.QuickTip": ["Ext.QuickTip"], "Ext.form.action.Load": ["Ext.form.Action.Load"], "Ext.form.field.ComboBox": ["Ext.form.ComboBox"], "Ext.layout.container.Border": ["Ext.layout.BorderLayout"], "Ext.data.JsonPStore": [], "Ext.layout.component.field.TextArea": [], "Ext.dom.AbstractHelper": [], "Ext.layout.container.Container": ["Ext.layout.ContainerLayout"], "Ext.util.Sortable": [], "Ext.selection.Model": ["Ext.AbstractSelectionModel"], "Ext.draw.CompositeSprite": [], "Ext.fx.Queue": [], "Ext.dd.StatusProxy": [], "Ext.form.field.Checkbox": ["Ext.form.Checkbox"], "Ext.XTemplateCompiler": [], "Ext.direct.Transaction": ["Ext.Direct.Transaction"], "Ext.util.Offset": [], "Ext.dom.Element": ["Ext.Element", "Ext.core.Element"], "Ext.view.DragZone": [], "Ext.util.KeyNav": ["Ext.KeyNav"], "Ext.form.field.File": ["Ext.form.FileUploadField", "Ext.ux.form.FileUploadField", "Ext.form.File"], "Ext.slider.Single": ["Ext.Slider", "Ext.form.SliderField", "Ext.slider.SingleSlider", "Ext.slider.Slider"], "Ext.panel.Proxy": ["Ext.dd.PanelProxy"], "Ext.fx.target.Target": [], "Ext.ComponentManager": ["Ext.ComponentMgr"], "Ext.grid.feature.GroupingSummary": [], "Ext.grid.property.HeaderContainer": ["Ext.grid.PropertyColumnModel"], "Ext.layout.component.BoundList": [], "Ext.tab.Bar": [], "Ext.app.Application": [], "Ext.ShadowPool": [], "Ext.layout.container.Accordion": ["Ext.layout.AccordionLayout"], "Ext.resizer.ResizeTracker": [], "Ext.layout.container.boxOverflow.None": ["Ext.layout.boxOverflow.None"], "Ext.panel.Tool": [], "Ext.tree.View": [], "Ext.ElementLoader": [], "Ext.grid.ColumnComponentLayout": [], "Ext.toolbar.Separator": ["Ext.Toolbar.Separator"], "Ext.dd.DragZone": [], "Ext.util.Renderable": [], "Ext.layout.component.FieldSet": [], "Ext.util.Bindable": [], "Ext.data.SortTypes": [], "Ext.util.Animate": [], "Ext.form.field.Date": ["Ext.form.DateField", "Ext.form.Date"], "Ext.Component": [], "Ext.chart.axis.Axis": ["Ext.chart.Axis"], "Ext.fx.target.CompositeSprite": [], "Ext.menu.DatePicker": [], "Ext.form.field.Picker": ["Ext.form.Picker"], "Ext.fx.Animator": [], "Ext.Ajax": [], "Ext.layout.component.Dock": ["Ext.layout.component.AbstractDock"], "Ext.util.Filter": [], "Ext.dd.DragDrop": [], "Ext.grid.Scroller": [], "Ext.view.View": ["Ext.DataView"], "Ext.data.association.BelongsTo": ["Ext.data.BelongsToAssociation"], "Ext.fx.target.Element": [], "Ext.draw.Surface": [], "Ext.dd.DDProxy": [], "Ext.data.AbstractStore": [], "Ext.form.action.StandardSubmit": [], "Ext.grid.Lockable": [], "Ext.dd.Registry": [], "Ext.picker.Month": ["Ext.MonthPicker"], "Ext.container.Container": ["Ext.Container"], "Ext.menu.Manager": ["Ext.menu.MenuMgr"], "Ext.util.KeyMap": ["Ext.KeyMap"], "Ext.data.Batch": [], "Ext.resizer.Handle": [], "Ext.util.ElementContainer": [], "Ext.grid.feature.Grouping": [], "Ext.tab.Panel": ["Ext.TabPanel"], "Ext.layout.component.Body": [], "Ext.layout.Context": [], "Ext.layout.component.field.ComboBox": [], "Ext.dd.DDTarget": [], "Ext.chart.Chart": [], "Ext.data.Field": [], "Ext.chart.series.Gauge": [], "Ext.data.StoreManager": ["Ext.StoreMgr", "Ext.data.StoreMgr", "Ext.StoreManager"], "Ext.tip.QuickTipManager": ["Ext.QuickTips"], "Ext.data.IdGenerator": [], "Ext.grid.plugin.Editing": [], "Ext.grid.RowEditor": [], "Ext.state.LocalStorageProvider": [], "Ext.form.action.Action": ["Ext.form.Action"], "Ext.ProgressBar": [], "Ext.tree.ViewDragZone": [], "Ext.data.reader.Array": ["Ext.data.ArrayReader"], "Ext.picker.Date": ["Ext.DatePicker"], "Ext.data.proxy.JsonP": ["Ext.data.ScriptTagProxy"], "Ext.chart.series.Area": [], "Ext.fx.Anim": [], "Ext.menu.Item": ["Ext.menu.TextItem"], "Ext.chart.Legend": [], "Ext.grid.plugin.HeaderReorderer": [], "Ext.layout.container.VBox": ["Ext.layout.VBoxLayout"], "Ext.view.DropZone": [], "Ext.layout.component.Button": [], "Ext.form.field.Hidden": ["Ext.form.Hidden"], "Ext.form.FieldContainer": [], "Ext.data.proxy.Server": ["Ext.data.ServerProxy"], "Ext.chart.series.Cartesian": ["Ext.chart.CartesianSeries", "Ext.chart.CartesianChart"], "Ext.grid.column.Column": ["Ext.grid.Column"], "Ext.data.ResultSet": [], "Ext.data.association.HasMany": ["Ext.data.HasManyAssociation"], "Ext.layout.container.Fit": ["Ext.layout.FitLayout"], "Ext.util.CSS": [], "Ext.layout.component.field.Field": [], "Ext.data.proxy.Ajax": ["Ext.data.HttpProxy", "Ext.data.AjaxProxy"], "Ext.form.Label": [], "Ext.data.writer.Writer": ["Ext.data.DataWriter", "Ext.data.Writer"], "Ext.view.BoundListKeyNav": [], "Ext.form.FieldSet": [], "Ext.XTemplateParser": [], "Ext.form.field.VTypes": ["Ext.form.VTypes"], "Ext.fx.PropertyHandler": [], "Ext.form.CheckboxGroup": [], "Ext.data.JsonP": [], "Ext.draw.engine.Vml": [], "Ext.layout.container.CheckboxGroup": [], "Ext.panel.Header": [], "Ext.app.Controller": [], "Ext.grid.plugin.CellEditing": [], "Ext.form.field.Time": ["Ext.form.TimeField", "Ext.form.Time"], "Ext.fx.CubicBezier": [], "Ext.button.Cycle": ["Ext.CycleButton"], "Ext.data.Tree": [], "Ext.ModelManager": ["Ext.ModelMgr"], "Ext.data.XmlStore": [], "Ext.grid.ViewDropZone": [], "Ext.grid.header.DropZone": [], "Ext.Layer": [], "Ext.util.HashMap": [], "Ext.grid.column.Template": ["Ext.grid.TemplateColumn"], "Ext.ComponentLoader": [], "Ext.EventObjectImpl": [], "Ext.form.FieldAncestor": [], "Ext.chart.axis.Gauge": [], "Ext.data.validations": [], "Ext.data.Connection": [], "Ext.dd.DropZone": [], "Ext.direct.ExceptionEvent": [], "Ext.resizer.Splitter": [], "Ext.form.RadioManager": [], "Ext.data.association.HasOne": ["Ext.data.HasOneAssociation"], "Ext.draw.Text": [], "Ext.window.MessageBox": [], "Ext.fx.target.CompositeElementCSS": [], "Ext.chart.series.Line": ["Ext.chart.LineSeries", "Ext.chart.LineChart"], "Ext.view.Table": [], "Ext.data.writer.Json": ["Ext.data.JsonWriter"], "Ext.fx.Manager": [], "Ext.fx.target.CompositeElement": [], "Ext.chart.Label": [], "Ext.grid.View": [], "Ext.Action": [], "Ext.form.Basic": ["Ext.form.BasicForm"], "Ext.container.Viewport": ["Ext.Viewport"], "Ext.state.Stateful": [], "Ext.grid.feature.RowBody": [], "Ext.form.field.Text": ["Ext.form.TextField", "Ext.form.Text"], "Ext.data.reader.Xml": ["Ext.data.XmlReader"], "Ext.grid.feature.AbstractSummary": [], "Ext.chart.axis.Category": ["Ext.chart.CategoryAxis"], "Ext.layout.container.Absolute": ["Ext.layout.AbsoluteLayout"], "Ext.data.reader.Json": ["Ext.data.JsonReader"], "Ext.util.TextMetrics": [], "Ext.data.TreeStore": [], "Ext.view.BoundList": ["Ext.BoundList"], "Ext.form.field.HtmlEditor": ["Ext.form.HtmlEditor"], "Ext.layout.container.Form": ["Ext.layout.FormLayout"], "Ext.chart.MaskLayer": [], "Ext.util.Observable": [], "Ext.resizer.BorderSplitterTracker": [], "Ext.util.LruCache": [], "Ext.tip.Tip": ["Ext.Tip"], "Ext.dom.CompositeElement": ["Ext.CompositeElement"], "Ext.grid.feature.RowWrap": [], "Ext.data.proxy.Client": ["Ext.data.ClientProxy"], "Ext.data.Types": [], "Ext.draw.SpriteDD": [], "Ext.layout.container.boxOverflow.Menu": ["Ext.layout.boxOverflow.Menu"], "Ext.LoadMask": [], "Ext.toolbar.Paging": ["Ext.PagingToolbar"], "Ext.data.association.Association": ["Ext.data.Association"], "Ext.tree.ViewDropZone": [], "Ext.grid.LockingView": [], "Ext.toolbar.Toolbar": ["Ext.Toolbar"], "Ext.tip.ToolTip": ["Ext.ToolTip"], "Ext.chart.Highlight": [], "Ext.state.Manager": [], "Ext.util.Inflector": [], "Ext.grid.Panel": ["Ext.list.ListView", "Ext.ListView", "Ext.grid.GridPanel"], "Ext.XTemplate": [], "Ext.data.NodeStore": [], "Ext.Shadow": [], "Ext.form.action.Submit": ["Ext.form.Action.Submit"], "Ext.form.Panel": ["Ext.FormPanel", "Ext.form.FormPanel"], "Ext.chart.series.Series": [], "Ext.perf.Accumulator": [], "Ext.data.Request": [], "Ext.dd.DD": [], "Ext.dom.CompositeElementLite": ["Ext.CompositeElementLite"], "Ext.toolbar.Fill": ["Ext.Toolbar.Fill"], "Ext.grid.RowNumberer": [], "Ext.data.proxy.WebStorage": ["Ext.data.WebStorageProxy"], "Ext.util.Floating": [], "Ext.form.action.DirectSubmit": ["Ext.form.Action.DirectSubmit"], "Ext.util.Cookies": [], "Ext.data.UuidGenerator": [], "Ext.util.Point": [], "Ext.fx.target.Component": [], "Ext.form.CheckboxManager": [], "Ext.form.field.Field": [], "Ext.form.field.Display": ["Ext.form.DisplayField", "Ext.form.Display"], "Ext.layout.container.Anchor": ["Ext.layout.AnchorLayout"], "Ext.layout.component.field.Text": [], "Ext.data.DirectStore": [], "Ext.data.BufferStore": [], "Ext.grid.ColumnLayout": [], "Ext.chart.series.Column": ["Ext.chart.ColumnSeries", "Ext.chart.ColumnChart", "Ext.chart.StackedColumnChart"], "Ext.Template": [], "Ext.AbstractComponent": [], "Ext.flash.Component": ["Ext.FlashComponent"], "Ext.form.field.Base": ["Ext.form.Field", "Ext.form.BaseField"], "Ext.data.SequentialIdGenerator": [], "Ext.grid.header.Container": [], "Ext.container.ButtonGroup": ["Ext.ButtonGroup"], "Ext.grid.column.Action": ["Ext.grid.ActionColumn"], "Ext.layout.component.field.Trigger": [], "Ext.layout.component.field.FieldContainer": [], "Ext.chart.Shape": [], "Ext.panel.DD": [], "Ext.container.AbstractContainer": [], "Ext.data.ArrayStore": [], "Ext.window.Window": ["Ext.Window"], "Ext.picker.Color": ["Ext.ColorPalette"], "Ext.grid.feature.Feature": [], "Ext.chart.theme.Theme": [], "Ext.util.ClickRepeater": [], "Ext.form.field.Spinner": ["Ext.form.Spinner"], "Ext.container.DockingContainer": [], "Ext.selection.DataViewModel": [], "Ext.dd.DragTracker": [], "Ext.dd.DragDropManager": ["Ext.dd.DragDropMgr", "Ext.dd.DDM"], "Ext.selection.CheckboxModel": [], "Ext.layout.container.Column": ["Ext.layout.ColumnLayout"], "Ext.menu.KeyNav": [], "Ext.draw.Matrix": [], "Ext.form.field.Number": ["Ext.form.NumberField", "Ext.form.Number"], "Ext.data.proxy.Direct": ["Ext.data.DirectProxy"], "Ext.chart.Navigation": [], "Ext.slider.Tip": [], "Ext.chart.theme.Base": [], "Ext.form.field.TextArea": ["Ext.form.TextArea"], "Ext.form.field.Radio": ["Ext.form.Radio"], "Ext.layout.component.ProgressBar": [], "Ext.chart.series.Pie": ["Ext.chart.PieSeries", "Ext.chart.PieChart"], "Ext.view.TableChunker": [], "Ext.tree.plugin.TreeViewDragDrop": [], "Ext.direct.Provider": [], "Ext.layout.Layout": [], "Ext.toolbar.TextItem": ["Ext.Toolbar.TextItem"], "Ext.dom.Helper": [], "Ext.util.AbstractMixedCollection": [], "Ext.data.JsonStore": [], "Ext.button.Split": ["Ext.SplitButton"], "Ext.dd.DropTarget": [], "Ext.direct.RemotingEvent": [], "Ext.draw.Sprite": [], "Ext.fx.target.Sprite": [], "Ext.data.proxy.LocalStorage": ["Ext.data.LocalStorageProxy"], "Ext.layout.component.Draw": [], "Ext.AbstractPlugin": [], "Ext.Editor": [], "Ext.chart.axis.Radial": [], "Ext.chart.Tip": [], "Ext.layout.container.Table": ["Ext.layout.TableLayout"], "Ext.chart.axis.Abstract": [], "Ext.data.proxy.Rest": ["Ext.data.RestProxy"], "Ext.util.Queue": [], "Ext.state.CookieProvider": [], "Ext.Img": [], "Ext.dd.DragSource": [], "Ext.grid.CellEditor": [], "Ext.layout.ClassList": [], "Ext.util.Sorter": [], "Ext.resizer.SplitterTracker": [], "Ext.panel.Table": [], "Ext.draw.Color": [], "Ext.chart.series.Bar": ["Ext.chart.BarSeries", "Ext.chart.BarChart", "Ext.chart.StackedBarChart"], "Ext.PluginManager": ["Ext.PluginMgr"], "Ext.util.ComponentDragger": [], "Ext.chart.series.Scatter": [], "Ext.chart.Callout": [], "Ext.data.Store": [], "Ext.grid.feature.Summary": [], "Ext.layout.component.Component": [], "Ext.util.ProtoElement": [], "Ext.direct.Manager": [], "Ext.data.proxy.Proxy": ["Ext.data.DataProxy", "Ext.data.Proxy"], "Ext.menu.CheckItem": [], "Ext.dom.AbstractElement": [], "Ext.layout.container.Card": ["Ext.layout.CardLayout"], "Ext.draw.Component": [], "Ext.toolbar.Item": ["Ext.Toolbar.Item"], "Ext.form.RadioGroup": [], "Ext.slider.Thumb": [], "Ext.grid.header.DragZone": [], "Ext.form.action.DirectLoad": ["Ext.form.Action.DirectLoad"], "Ext.picker.Time": [], "Ext.resizer.BorderSplitter": [], "Ext.ZIndexManager": ["Ext.WindowGroup"], "Ext.menu.ColorPicker": [], "Ext.menu.Menu": [], "Ext.chart.LegendItem": [], "Ext.toolbar.Spacer": ["Ext.Toolbar.Spacer"], "Ext.panel.Panel": ["Ext.Panel"], "Ext.util.Memento": [], "Ext.data.proxy.Memory": ["Ext.data.MemoryProxy"], "Ext.chart.axis.Time": ["Ext.chart.TimeAxis"], "Ext.grid.plugin.DragDrop": [], "Ext.layout.component.Tab": [], "Ext.ComponentQuery": [], "Ext.draw.engine.SvgExporter": [], "Ext.grid.feature.Chunking": [], "Ext.layout.container.Auto": [], "Ext.view.AbstractView": [], "Ext.util.Region": [], "Ext.draw.Draw": [], "Ext.fx.target.ElementCSS": [], "Ext.grid.PagingScroller": [], "Ext.layout.component.field.HtmlEditor": [], "Ext.data.proxy.SessionStorage": ["Ext.data.SessionStorageProxy"], "Ext.app.EventBus": [], "Ext.menu.Separator": [], "Ext.util.History": ["Ext.History"], "Ext.direct.Event": [], "Ext.direct.RemotingMethod": [], "Ext.dd.ScrollManager": [], "Ext.chart.Mask": [], "Ext.selection.CellModel": [], "Ext.view.TableLayout": [], "Ext.state.Provider": [], "Ext.layout.container.Editor": [], "Ext.data.Errors": [], "Ext.dom.AbstractQuery": [], "Ext.selection.TreeModel": [], "Ext.form.Labelable": [], "Ext.grid.column.Number": ["Ext.grid.NumberColumn"], "Ext.draw.engine.Svg": [], "Ext.grid.property.Grid": ["Ext.grid.PropertyGrid"], "Ext.FocusManager": ["Ext.FocusMgr"], "Ext.AbstractManager": [], "Ext.chart.series.Radar": [], "Ext.grid.property.Property": ["Ext.PropGridProperty"], "Ext.chart.TipSurface": [], "Ext.grid.column.Boolean": ["Ext.grid.BooleanColumn"], "Ext.direct.PollingProvider": [], "Ext.grid.plugin.HeaderResizer": [], "Ext.data.writer.Xml": ["Ext.data.XmlWriter"], "Ext.tree.Column": [], "Ext.slider.Multi": ["Ext.slider.MultiSlider"], "Ext.panel.AbstractPanel": [], "Ext.layout.component.field.Slider": [], "Ext.chart.axis.Numeric": ["Ext.chart.NumericAxis"], "Ext.layout.container.boxOverflow.Scroller": ["Ext.layout.boxOverflow.Scroller"], "Ext.data.Operation": [], "Ext.layout.container.HBox": ["Ext.layout.HBoxLayout"], "Ext.resizer.Resizer": ["Ext.Resizable"], "Ext.selection.RowModel": [], "Ext.layout.ContextItem": [], "Ext.util.MixedCollection": [], "Ext.perf.Monitor": ["Ext.Perf"]});
Ext.ClassManager.addNameAliasMappings({"Ext.draw.engine.ImageExporter": [], "Ext.layout.component.Auto": ["layout.autocomponent"], "Ext.grid.property.Store": [], "Ext.layout.container.Box": ["layout.box"], "Ext.direct.JsonProvider": ["direct.jsonprovider"], "Ext.tree.Panel": ["widget.treepanel"], "Ext.data.Model": [], "Ext.data.reader.Reader": [], "Ext.tab.Tab": ["widget.tab"], "Ext.button.Button": ["widget.button"], "Ext.util.Grouper": [], "Ext.util.TaskRunner": [], "Ext.direct.RemotingProvider": ["direct.remotingprovider"], "Ext.data.NodeInterface": [], "Ext.grid.column.Date": ["widget.datecolumn"], "Ext.form.field.Trigger": ["widget.triggerfield", "widget.trigger"], "Ext.grid.plugin.RowEditing": ["plugin.rowediting"], "Ext.tip.QuickTip": ["widget.quicktip"], "Ext.form.action.Load": ["formaction.load"], "Ext.form.field.ComboBox": ["widget.combobox", "widget.combo"], "Ext.layout.container.Border": ["layout.border"], "Ext.data.JsonPStore": ["store.jsonp"], "Ext.layout.component.field.TextArea": ["layout.textareafield"], "Ext.dom.AbstractHelper": [], "Ext.layout.container.Container": [], "Ext.util.Sortable": [], "Ext.selection.Model": [], "Ext.draw.CompositeSprite": [], "Ext.fx.Queue": [], "Ext.dd.StatusProxy": [], "Ext.form.field.Checkbox": ["widget.checkboxfield", "widget.checkbox"], "Ext.XTemplateCompiler": [], "Ext.direct.Transaction": ["direct.transaction"], "Ext.util.Offset": [], "Ext.dom.Element": [], "Ext.view.DragZone": [], "Ext.util.KeyNav": [], "Ext.form.field.File": ["widget.filefield", "widget.fileuploadfield"], "Ext.slider.Single": ["widget.slider", "widget.sliderfield"], "Ext.panel.Proxy": [], "Ext.fx.target.Target": [], "Ext.ComponentManager": [], "Ext.grid.feature.GroupingSummary": ["feature.groupingsummary"], "Ext.grid.property.HeaderContainer": [], "Ext.layout.component.BoundList": ["layout.boundlist"], "Ext.tab.Bar": ["widget.tabbar"], "Ext.app.Application": [], "Ext.ShadowPool": [], "Ext.layout.container.Accordion": ["layout.accordion"], "Ext.resizer.ResizeTracker": [], "Ext.layout.container.boxOverflow.None": [], "Ext.panel.Tool": ["widget.tool"], "Ext.tree.View": ["widget.treeview"], "Ext.ElementLoader": [], "Ext.grid.ColumnComponentLayout": ["layout.columncomponent"], "Ext.toolbar.Separator": ["widget.tbseparator"], "Ext.dd.DragZone": [], "Ext.util.Renderable": [], "Ext.layout.component.FieldSet": ["layout.fieldset"], "Ext.util.Bindable": [], "Ext.data.SortTypes": [], "Ext.util.Animate": [], "Ext.form.field.Date": ["widget.datefield"], "Ext.Component": ["widget.component", "widget.box"], "Ext.chart.axis.Axis": [], "Ext.fx.target.CompositeSprite": [], "Ext.menu.DatePicker": ["widget.datemenu"], "Ext.form.field.Picker": ["widget.pickerfield"], "Ext.fx.Animator": [], "Ext.Ajax": [], "Ext.layout.component.Dock": ["layout.dock"], "Ext.util.Filter": [], "Ext.dd.DragDrop": [], "Ext.grid.Scroller": [], "Ext.view.View": ["widget.dataview"], "Ext.data.association.BelongsTo": ["association.belongsto"], "Ext.fx.target.Element": [], "Ext.draw.Surface": [], "Ext.dd.DDProxy": [], "Ext.data.AbstractStore": [], "Ext.form.action.StandardSubmit": ["formaction.standardsubmit"], "Ext.grid.Lockable": [], "Ext.dd.Registry": [], "Ext.picker.Month": ["widget.monthpicker"], "Ext.container.Container": ["widget.container"], "Ext.menu.Manager": [], "Ext.util.KeyMap": [], "Ext.data.Batch": [], "Ext.resizer.Handle": [], "Ext.util.ElementContainer": [], "Ext.grid.feature.Grouping": ["feature.grouping"], "Ext.tab.Panel": ["widget.tabpanel"], "Ext.layout.component.Body": ["layout.body"], "Ext.layout.Context": [], "Ext.layout.component.field.ComboBox": ["layout.combobox"], "Ext.dd.DDTarget": [], "Ext.chart.Chart": ["widget.chart"], "Ext.data.Field": ["data.field"], "Ext.chart.series.Gauge": ["series.gauge"], "Ext.data.StoreManager": [], "Ext.tip.QuickTipManager": [], "Ext.data.IdGenerator": [], "Ext.grid.plugin.Editing": ["editing.editing"], "Ext.grid.RowEditor": [], "Ext.state.LocalStorageProvider": ["state.localstorage"], "Ext.form.action.Action": [], "Ext.ProgressBar": ["widget.progressbar"], "Ext.tree.ViewDragZone": [], "Ext.data.reader.Array": ["reader.array"], "Ext.picker.Date": ["widget.datepicker"], "Ext.data.proxy.JsonP": ["proxy.jsonp", "proxy.scripttag"], "Ext.chart.series.Area": ["series.area"], "Ext.fx.Anim": [], "Ext.menu.Item": ["widget.menuitem"], "Ext.chart.Legend": [], "Ext.grid.plugin.HeaderReorderer": ["plugin.gridheaderreorderer"], "Ext.layout.container.VBox": ["layout.vbox"], "Ext.view.DropZone": [], "Ext.layout.component.Button": ["layout.button"], "Ext.form.field.Hidden": ["widget.hiddenfield", "widget.hidden"], "Ext.form.FieldContainer": ["widget.fieldcontainer"], "Ext.data.proxy.Server": ["proxy.server"], "Ext.chart.series.Cartesian": [], "Ext.grid.column.Column": ["widget.gridcolumn"], "Ext.data.ResultSet": [], "Ext.data.association.HasMany": ["association.hasmany"], "Ext.layout.container.Fit": ["layout.fit"], "Ext.util.CSS": [], "Ext.layout.component.field.Field": ["layout.field"], "Ext.data.proxy.Ajax": ["proxy.ajax"], "Ext.form.Label": ["widget.label"], "Ext.data.writer.Writer": ["writer.base"], "Ext.view.BoundListKeyNav": [], "Ext.form.FieldSet": ["widget.fieldset"], "Ext.XTemplateParser": [], "Ext.form.field.VTypes": [], "Ext.fx.PropertyHandler": [], "Ext.form.CheckboxGroup": ["widget.checkboxgroup"], "Ext.data.JsonP": [], "Ext.draw.engine.Vml": [], "Ext.layout.container.CheckboxGroup": ["layout.checkboxgroup"], "Ext.panel.Header": ["widget.header"], "Ext.app.Controller": [], "Ext.grid.plugin.CellEditing": ["plugin.cellediting"], "Ext.form.field.Time": ["widget.timefield"], "Ext.fx.CubicBezier": [], "Ext.button.Cycle": ["widget.cycle"], "Ext.data.Tree": ["data.tree"], "Ext.ModelManager": [], "Ext.data.XmlStore": ["store.xml"], "Ext.grid.ViewDropZone": [], "Ext.grid.header.DropZone": [], "Ext.Layer": [], "Ext.util.HashMap": [], "Ext.grid.column.Template": ["widget.templatecolumn"], "Ext.ComponentLoader": [], "Ext.EventObjectImpl": [], "Ext.form.FieldAncestor": [], "Ext.chart.axis.Gauge": ["axis.gauge"], "Ext.data.validations": [], "Ext.data.Connection": [], "Ext.dd.DropZone": [], "Ext.direct.ExceptionEvent": ["direct.exception"], "Ext.resizer.Splitter": ["widget.splitter"], "Ext.form.RadioManager": [], "Ext.data.association.HasOne": ["association.hasone"], "Ext.draw.Text": ["widget.text"], "Ext.window.MessageBox": ["widget.messagebox"], "Ext.fx.target.CompositeElementCSS": [], "Ext.chart.series.Line": ["series.line"], "Ext.view.Table": ["widget.tableview"], "Ext.data.writer.Json": ["writer.json"], "Ext.fx.Manager": [], "Ext.fx.target.CompositeElement": [], "Ext.chart.Label": [], "Ext.grid.View": ["widget.gridview"], "Ext.Action": [], "Ext.form.Basic": [], "Ext.container.Viewport": ["widget.viewport"], "Ext.state.Stateful": [], "Ext.grid.feature.RowBody": ["feature.rowbody"], "Ext.form.field.Text": ["widget.textfield"], "Ext.data.reader.Xml": ["reader.xml"], "Ext.grid.feature.AbstractSummary": ["feature.abstractsummary"], "Ext.chart.axis.Category": ["axis.category"], "Ext.layout.container.Absolute": ["layout.absolute"], "Ext.data.reader.Json": ["reader.json"], "Ext.util.TextMetrics": [], "Ext.data.TreeStore": ["store.tree"], "Ext.view.BoundList": ["widget.boundlist"], "Ext.form.field.HtmlEditor": ["widget.htmleditor"], "Ext.layout.container.Form": ["layout.form"], "Ext.chart.MaskLayer": [], "Ext.util.Observable": [], "Ext.resizer.BorderSplitterTracker": [], "Ext.util.LruCache": [], "Ext.tip.Tip": [], "Ext.dom.CompositeElement": [], "Ext.grid.feature.RowWrap": ["feature.rowwrap"], "Ext.data.proxy.Client": [], "Ext.data.Types": [], "Ext.draw.SpriteDD": [], "Ext.layout.container.boxOverflow.Menu": [], "Ext.LoadMask": ["widget.loadmask"], "Ext.toolbar.Paging": ["widget.pagingtoolbar"], "Ext.data.association.Association": [], "Ext.tree.ViewDropZone": [], "Ext.grid.LockingView": [], "Ext.toolbar.Toolbar": ["widget.toolbar"], "Ext.tip.ToolTip": ["widget.tooltip"], "Ext.chart.Highlight": [], "Ext.state.Manager": [], "Ext.util.Inflector": [], "Ext.grid.Panel": ["widget.gridpanel", "widget.grid"], "Ext.XTemplate": [], "Ext.data.NodeStore": ["store.node"], "Ext.Shadow": [], "Ext.form.action.Submit": ["formaction.submit"], "Ext.form.Panel": ["widget.form"], "Ext.chart.series.Series": [], "Ext.perf.Accumulator": [], "Ext.data.Request": [], "Ext.dd.DD": [], "Ext.dom.CompositeElementLite": [], "Ext.toolbar.Fill": ["widget.tbfill"], "Ext.grid.RowNumberer": ["widget.rownumberer"], "Ext.data.proxy.WebStorage": [], "Ext.util.Floating": [], "Ext.form.action.DirectSubmit": ["formaction.directsubmit"], "Ext.util.Cookies": [], "Ext.data.UuidGenerator": ["idgen.uuid"], "Ext.util.Point": [], "Ext.fx.target.Component": [], "Ext.form.CheckboxManager": [], "Ext.form.field.Field": [], "Ext.form.field.Display": ["widget.displayfield"], "Ext.layout.container.Anchor": ["layout.anchor"], "Ext.layout.component.field.Text": ["layout.textfield"], "Ext.data.DirectStore": ["store.direct"], "Ext.data.BufferStore": ["store.buffer"], "Ext.grid.ColumnLayout": ["layout.gridcolumn"], "Ext.chart.series.Column": ["series.column"], "Ext.Template": [], "Ext.AbstractComponent": [], "Ext.flash.Component": ["widget.flash"], "Ext.form.field.Base": ["widget.field"], "Ext.data.SequentialIdGenerator": ["idgen.sequential"], "Ext.grid.header.Container": ["widget.headercontainer"], "Ext.container.ButtonGroup": ["widget.buttongroup"], "Ext.grid.column.Action": ["widget.actioncolumn"], "Ext.layout.component.field.Trigger": ["layout.triggerfield"], "Ext.layout.component.field.FieldContainer": ["layout.fieldcontainer"], "Ext.chart.Shape": [], "Ext.panel.DD": [], "Ext.container.AbstractContainer": [], "Ext.data.ArrayStore": ["store.array"], "Ext.window.Window": ["widget.window"], "Ext.picker.Color": ["widget.colorpicker"], "Ext.grid.feature.Feature": ["feature.feature"], "Ext.chart.theme.Theme": [], "Ext.util.ClickRepeater": [], "Ext.form.field.Spinner": ["widget.spinnerfield"], "Ext.container.DockingContainer": [], "Ext.selection.DataViewModel": [], "Ext.dd.DragTracker": [], "Ext.dd.DragDropManager": [], "Ext.selection.CheckboxModel": ["selection.checkboxmodel"], "Ext.layout.container.Column": ["layout.column"], "Ext.menu.KeyNav": [], "Ext.draw.Matrix": [], "Ext.form.field.Number": ["widget.numberfield"], "Ext.data.proxy.Direct": ["proxy.direct"], "Ext.chart.Navigation": [], "Ext.slider.Tip": ["widget.slidertip"], "Ext.chart.theme.Base": [], "Ext.form.field.TextArea": ["widget.textareafield", "widget.textarea"], "Ext.form.field.Radio": ["widget.radiofield", "widget.radio"], "Ext.layout.component.ProgressBar": ["layout.progressbar"], "Ext.chart.series.Pie": ["series.pie"], "Ext.view.TableChunker": [], "Ext.tree.plugin.TreeViewDragDrop": ["plugin.treeviewdragdrop"], "Ext.direct.Provider": ["direct.provider"], "Ext.layout.Layout": [], "Ext.toolbar.TextItem": ["widget.tbtext"], "Ext.dom.Helper": [], "Ext.util.AbstractMixedCollection": [], "Ext.data.JsonStore": ["store.json"], "Ext.button.Split": ["widget.splitbutton"], "Ext.dd.DropTarget": [], "Ext.direct.RemotingEvent": ["direct.rpc"], "Ext.draw.Sprite": [], "Ext.fx.target.Sprite": [], "Ext.data.proxy.LocalStorage": ["proxy.localstorage"], "Ext.layout.component.Draw": ["layout.draw"], "Ext.AbstractPlugin": [], "Ext.Editor": ["widget.editor"], "Ext.chart.axis.Radial": ["axis.radial"], "Ext.chart.Tip": [], "Ext.layout.container.Table": ["layout.table"], "Ext.chart.axis.Abstract": [], "Ext.data.proxy.Rest": ["proxy.rest"], "Ext.util.Queue": [], "Ext.state.CookieProvider": [], "Ext.Img": ["widget.image", "widget.imagecomponent"], "Ext.dd.DragSource": [], "Ext.grid.CellEditor": [], "Ext.layout.ClassList": [], "Ext.util.Sorter": [], "Ext.resizer.SplitterTracker": [], "Ext.panel.Table": ["widget.tablepanel"], "Ext.draw.Color": [], "Ext.chart.series.Bar": ["series.bar"], "Ext.PluginManager": [], "Ext.util.ComponentDragger": [], "Ext.chart.series.Scatter": ["series.scatter"], "Ext.chart.Callout": [], "Ext.data.Store": ["store.store"], "Ext.grid.feature.Summary": ["feature.summary"], "Ext.layout.component.Component": [], "Ext.util.ProtoElement": [], "Ext.direct.Manager": [], "Ext.data.proxy.Proxy": ["proxy.proxy"], "Ext.menu.CheckItem": ["widget.menucheckitem"], "Ext.dom.AbstractElement": [], "Ext.layout.container.Card": ["layout.card"], "Ext.draw.Component": ["widget.draw"], "Ext.toolbar.Item": ["widget.tbitem"], "Ext.form.RadioGroup": ["widget.radiogroup"], "Ext.slider.Thumb": [], "Ext.grid.header.DragZone": [], "Ext.form.action.DirectLoad": ["formaction.directload"], "Ext.picker.Time": ["widget.timepicker"], "Ext.resizer.BorderSplitter": ["widget.bordersplitter"], "Ext.ZIndexManager": [], "Ext.menu.ColorPicker": ["widget.colormenu"], "Ext.menu.Menu": ["widget.menu"], "Ext.chart.LegendItem": [], "Ext.toolbar.Spacer": ["widget.tbspacer"], "Ext.panel.Panel": ["widget.panel"], "Ext.util.Memento": [], "Ext.data.proxy.Memory": ["proxy.memory"], "Ext.chart.axis.Time": ["axis.time"], "Ext.grid.plugin.DragDrop": ["plugin.gridviewdragdrop"], "Ext.layout.component.Tab": ["layout.tab"], "Ext.ComponentQuery": [], "Ext.draw.engine.SvgExporter": [], "Ext.grid.feature.Chunking": ["feature.chunking"], "Ext.layout.container.Auto": ["layout.auto", "layout.autocontainer"], "Ext.view.AbstractView": [], "Ext.util.Region": [], "Ext.draw.Draw": [], "Ext.fx.target.ElementCSS": [], "Ext.grid.PagingScroller": [], "Ext.layout.component.field.HtmlEditor": ["layout.htmleditor"], "Ext.data.proxy.SessionStorage": ["proxy.sessionstorage"], "Ext.app.EventBus": [], "Ext.menu.Separator": ["widget.menuseparator"], "Ext.util.History": [], "Ext.direct.Event": ["direct.event"], "Ext.direct.RemotingMethod": [], "Ext.dd.ScrollManager": [], "Ext.chart.Mask": [], "Ext.selection.CellModel": ["selection.cellmodel"], "Ext.view.TableLayout": ["layout.tableview"], "Ext.state.Provider": [], "Ext.layout.container.Editor": ["layout.editor"], "Ext.data.Errors": [], "Ext.dom.AbstractQuery": [], "Ext.selection.TreeModel": ["selection.treemodel"], "Ext.form.Labelable": [], "Ext.grid.column.Number": ["widget.numbercolumn"], "Ext.draw.engine.Svg": [], "Ext.grid.property.Grid": ["widget.propertygrid"], "Ext.FocusManager": [], "Ext.AbstractManager": [], "Ext.chart.series.Radar": ["series.radar"], "Ext.grid.property.Property": [], "Ext.chart.TipSurface": [], "Ext.grid.column.Boolean": ["widget.booleancolumn"], "Ext.direct.PollingProvider": ["direct.pollingprovider"], "Ext.grid.plugin.HeaderResizer": ["plugin.gridheaderresizer"], "Ext.data.writer.Xml": ["writer.xml"], "Ext.tree.Column": ["widget.treecolumn"], "Ext.slider.Multi": ["widget.multislider"], "Ext.panel.AbstractPanel": [], "Ext.layout.component.field.Slider": ["layout.sliderfield"], "Ext.chart.axis.Numeric": ["axis.numeric"], "Ext.layout.container.boxOverflow.Scroller": [], "Ext.data.Operation": [], "Ext.layout.container.HBox": ["layout.hbox"], "Ext.resizer.Resizer": [], "Ext.selection.RowModel": ["selection.rowmodel"], "Ext.layout.ContextItem": [], "Ext.util.MixedCollection": [], "Ext.perf.Monitor": []});