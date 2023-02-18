/**
 * Minified by jsDelivr using Terser v5.15.1.
 * Original file: /npm/lemonadejs@2.8.11/dist/lemonade.js
 *
 * Do NOT use SRI with dynamically generated files! More information: https://www.jsdelivr.com/using-sri-with-dynamic-files
 */
!function (e, t) {
    "object" == typeof exports && "undefined" != typeof module
        ? module.exports = t()
        : "function" == typeof define && define.amd
            ? define(t)
            : e.lemonade = t()
}(this, (function () {
    "use strict";
    var e = null;
    "undefined" != typeof document && (
        e = document.lemonadejs
            ? document.lemonadejs
            : document.lemonadejs =
                { queue: [],
                    container: {}
                }
    );
    var t = function (e) {
            return e instanceof Element || e instanceof HTMLDocument
        },
        n = function (e, t) {
            return m.element(e, this, t)
        },
        i = function (t, n) {
            var i = null,
                r = null;
            if (r = t.lemon) {
                if (r.queue)
                    for (; i = r.queue.shift();)
                        e.queue.push(i);


                "function" == typeof r.self.onload && e.queue.push(r
                    .self
                    .onload
                    .bind(r.self, t))
            }
            if (document.body.contains(n) && e && e.queue && e.queue.length)
                for (; i = e.queue.shift();)
                    i()


        },
        r = function (e) {
            var t = null;
            if ("function" == typeof e.val)
                t = e.val();
            else if ("SELECT" == e.tagName && e.getAttribute("multiple")) {
                t = [];
                for (var n = 0; n < e.options.length; n++)
                    e.options[n].selected && t.push(e.options[n].value)

            } else
                t = "checkbox" == e.type
                    ? e.checked && e.getAttribute("value")
                        ? e.value
                        : e.checked
                    : e.getAttribute("contenteditable")
                        ? e.innerHTML
                        : e.value;

            return t
        },
        o = function (e, t, n) {
            if ("value" === n)
                if ("function" == typeof e.val)
                    e.val() != t && e.val(t);

                else if ("SELECT" == e.tagName && e.getAttribute("multiple"))
                    for (var i = 0; i < e.children.length; i++)
                        e.children[i].selected = t.indexOf(e.children[i].value) >= 0;

                else
                    "checkbox" == e.type
                        ? e.checked = !(! t || "0" === t || "false" === t)
                        : "radio" == e.type
                            ? (e.checked =! 1, e.value == t && (e.checked =! 0))
                            : e.getAttribute && e.getAttribute("contenteditable")
                                ? e.innerHTML != t && (e.innerHTML = t)
                                : e.value = t;
            else
                "@src" == n
                    ? (t || (t = e.getAttribute("default")), t && (e.src = t))
                    : void 0 !== e[n] || "function" == typeof t || "object" == typeof t
                        ? e[n] = t
                        : e.setAttribute(n, t)

        },
        l = function (e) {
            return Function("self", '"use strict";return (' + e + ")")(this)
        },
        s = function (e, t) {
            var n,
                i,
                r,
                s,
                a,
                c = null;
            if (i = this.tracking[e]) {
                for (n = this.self, c = 0; c < i.length; c++)
                    s = i[c].element,
                        r = l.call(n, i[c].v),
                        "@loop" == (a = i[c].property)
                            ? p.call(s, r, n, this.components)
                            : s.self
                                ? s.self[a] = r
                                : (o(s, r, a), this.tag && "self.value" == i[c].v && this.tag["@bind"] && this.self.parent[this.tag["@bind"]] !== r && (this.self.parent[this.tag["@bind"]] = r));

                t || "function" != typeof n.onchange || n.onchange.call(s, e, i, n)
            }
        },
        a = function (e) {
            var t = this,
                n = !0;
            Object.defineProperty(this.self, e, {
                set: function (i) {
                    t.state[e] = i,
                        s.call(t, e, n),
                        n = !1
                },
                get: function () {
                    return t.state[e]
                },
                configurable: !0,
                enumerable: !0
            })
        },
        c = function (e, t, n) {
            var i = this.self,
                r = l.call(i, t.v);
            if (void 0 === r && (r = ""), "textContent" == n) {
                var s = document.createTextNode(r);
                e.childNodes[0]
                    ? e.insertBefore(s, e.childNodes[0].splitText(t.p))
                    : e.appendChild(s)
            } else if ("@loop" == n)
                s = e;
            else {
                s = e;
                o(e, r, n)
            }
            var c = t.v.match(/self\.([a-zA-Z0-9_].*?)*/g);
            if (c && c.length)
                for (var u = 0; u < c.length; u++) {
                    var f = c[u].replace("self.", ""),
                        h = !1,
                        d = this.self[f];
                    void 0 === d && (d = ""),
                    this.tracking[f] || (this.tracking[f] =[], h =! 0),
                        this.tracking[f].push({element: s, property: n, v: t.v}),
                    h && a.call(this, f),
                        this.self[f] = d
                }

        },
        u = function (e, t) {
            var n = [],
                i = 0,
                r = function (e, t, r, o) {
                    return n.push({
                        p: r - i,
                        v: t
                    }),
                        i += e.length,
                        ""
                };
            if (
                e.getAttribute && e.getAttribute(t)
                    ? e.setAttribute(t, e.getAttribute(t).replace(/{{(.*?)}}/g, r))
                    : "string" == typeof e[t] && e[t] && (e[t] = e[t].replace(/{{(.*?)}}/g, r)),
                    n.length
            ) {
                1 != n.length || "textContent" != t || e.innerText || (t = "innerHTML");
                for (var o = n.length - 1; o >= 0; o--)
                    c.call(this, e, n[o], t)

            }
        },
        f = function (e) {
            var t = {},
                n = null,
                i = this.attributes;
            if (i && i.length)
                for (var r = 0; r < i.length; r++)
                    n = i[r].name,
                        1 == e && void 0 !== this[n]
                            ? t[n] = this[n]
                            : t[n] = i[r].value;


            return t
        },
        h = function (e) {
            var t = this,
                n = null,
                i = f.call(e);
            if (this.components) {
                for (var o = Object.keys(this.components), l = 0; l < o.length; l++)
                    this.components[o[l].toUpperCase()] = this.components[o[l]];

                var a = e.tagName;
                "function" == typeof(n = this.components[a]) && (e.handler = n)
            }
            if (! e.getAttribute("@loop") && ! e.getAttribute("lm-loop") || n || (n =! 0, e.parent = e), n && (e.self =
                {}, e.template = e.innerHTML, e.innerHTML = ""), (o = Object.keys(i)).length)
                for (l = 0; l < o.length; l++)
                    if (e.handler || "on" != o[l].substring(0, 2)) {
                        var p = i[o[l]].replace("self.", "");
                        "@ready" == o[l] || "lm-ready" == o[l]
                            ? (this.queue.push(Function("self", i[o[l]]).bind(e, this.self)), e.removeAttribute(o[l]))
                            : "@ref" == o[l] || "lm-ref" == o[l]
                                ? (
                                    this.self[p] = e.self
                                        ? e.self
                                        : e,
                                        e.removeAttribute(o[l])
                                )
                                : "@bind" == o[l] || "lm-bind" == o[l]
                                    ? (e.oninput = function (e, t) {
                                        this.state[t] = r(e),
                                            s.call(this, t)
                                    }.bind(this, e, p), c.call(this, e, {
                                        v: i[o[l]]
                                    }, "value"), e[o[l]] = p, e.removeAttribute(o[l]))
                                    : "@loop" == o[l] || "lm-loop" == o[l]
                                        ? (c.call(this, e, {
                                            v: i[o[l]]
                                        }, "@loop"), e.loop = this.self[p], e.removeAttribute(o[l]))
                                        : "@src" == o[l] || "lm-src" == o[l]
                                            ? (c.call(this, e, {
                                                v: i[o[l]]
                                            }, "@src"), e.removeAttribute(o[l]))
                                            : (u.call(this, e, o[l]), document.dictionary && (n = m.translate(i[o[l]])) && e.setAttribute(o[l], n))
                    }

                    else {
                        let n = o[l].toLowerCase(),
                            r = i[o[l]];
                        e.removeAttribute(n),
                            e.addEventListener(o[l].substring(2), (function (e) {
                                Function("self", "e", r).call(this, t.self, e)
                            }))
                    }
            if (e.children.length) {
                for (n =[], l = 0; l < e.children.length; l++)
                    n.push(e.children[l]);

                for (l = 0; l < n.length; l++)
                    h.call(this, n[l])

            } else
                e.textContent && (u.call(this, e, "textContent"), document.dictionary && (n = m.translate(e.innerText)) && (e.innerText = n));

            var v = e.handler;
            if ("function" == typeof v) {
                if (void 0 === e.loop) {
                    var g = e.parentNode,
                        b = m.setProperties.call(e.self, f.call(e, !0), !0);
                    d(b, "parent", this.self),
                        m.render(v, g, b, e.template, e, t.components)
                }
                e.remove()
            }
        },
        d = function (e, t, n) {
            Object.defineProperty(e, t, {
                enumerable: !1,
                configurable: !0,
                get: function () {
                    return n
                }
            })
        },
        p = function (e, t, i) {
            var r = null;
            this.parent || (this.parent = this.parentNode);
            var o = this.parent,
                l = this.handler || n,
                s = (r = this.template, []);
            if (e.length)
                for (let n = 0; n < e.length; n++) {
                    let a = e[n].el;
                    a || (d(e[n], "parent", t), a = m.render(l, o, e[n], r, null, i)),
                    "false" === o.getAttribute("unique") && d(e[n], "el", null),
                        s.push(a)
                }

            for (; o.children[0];)
                o.children[0].remove();

            for (; r = s.shift();)
                o.appendChild(r)

        },
        m = {};
    return m.render = function (e, n, r, o, l, s) {
        var a,
            c = e;
        if (! t(n))
            return console.log("Not valid DOM"),
                !1;

        if (r || (r =
            {}), "function" == typeof e && (
            "function" == typeof(a = e) && /^class\s/.test(Function
                .prototype
                .toString
                .call(a))
                ? (e = new e(r), l && (l.self = e), e = m.element(e.render(o, s), e))
                : e = e.call(r, o, s),
                ! t(e)
        ))
            return console.log("Component did not return a valid DOM"),
                !1;

        if ("ROOT" == e.tagName)
            for (e.lemon.root =[]; e.firstChild;)
                e
                    .lemon
                    .root
                    .push(e.firstChild),
                    l
                        ? n.insertBefore(e.firstChild, l)
                        : n.appendChild(e.firstChild);

        else
            l
                ? n.insertBefore(e, l)
                : n.appendChild(e);

        return i(e, n),
            e.lemon.component = c,
        l && (e.lemon.tag = l),
            e
    },
        m.element = function (e, n, i) {
            var r = {
                self: n || {},
                state: {},
                tracking: {},
                queue: []
            };
            if (i && (r.components = i), t(e))
                o = e;
            else {
                var o;
                if (
                    e =( e = e.replace(/(<(([A-Z]{1}|[a-z]*-){1}[a-zA-Z0-9_-]+)[^>]*)(\/|\/.{1})>/gm, "$1></$2>"))
                        .replace(/<>/gi, "<root>")
                        .replace(/<\/>/gi, "</root>")
                        .trim(),
                        (o = document.createElement("template")).innerHTML = e,
                        o.content
                            ? o = o.content
                            : (o = document.createElement("div")).innerHTML = e,
                    o.childNodes.length > 1
                )
                    return void console.error("Single root required");

                o = o.firstChild
            }
            return h.call(r, o),
                d(n, "refresh", (function (e) {
                    m.refresh.call(r, e)
                })),
                d(n, "el", o),
                o.lemon = r,
                o
        },
        m.template = m.element,
        m.refresh = function (e) {
            if (void 0 !== e)
                s.call(this, e);
            else {
                let e = this.self.el;
                if ("ROOT" == e.tagName)
                    for (e = this.root, m.render(this.component, e[0].parentNode, this.self, null, e[0]); e[0];)
                        e.shift().remove();

                else
                    m.render(this.component, e.parentNode, this.self, null, e),
                        e.parentNode.removeChild(e)

            }
        },
        m.blender = function (e, t, n) {
            return m.render(m.element(e, t), n, t)
        },
        m.apply = function (e, t, n) {
            m.element(e, t, n),
                i(e, e)
        },
        m.getProperties = function (e) {
            var t = {};
            for (var n in e)
                t[n] = this[n];

            return t
        },
        m.setProperties = function (e, t) {
            for (var n in e)
                (this.hasOwnProperty(n) || t) && (this[n] = e[n]);

            return this
        },
        m.resetProperties = function (e) {
            for (var t in e)
                this[t] = ""

        },
        m.get = function (t) {
            return e.container[t]
        },
        m.set = function (t, n, i) {
            if (e.container[t] = n, "function" == typeof n && !0 === i) {
                e.container[t].storage = !0;
                var r = window.localStorage.getItem(t);
                r && n(r = JSON.parse(r))
            }
        },
        m.dispatch = function (t, n) {
            var i = e.container[t];
            "function" == typeof i && (i(n), !0 === i.storage && window.localStorage.setItem(t, JSON.stringify(n)))
        },
        m.translate = function (e) {
            if ("^^[" == e.substr(0, 3) && "]^^" == e.substr(-3))
                return e = e.replace("^^[", "").replace("]^^", ""),
                    document.dictionary[e]
                        ? document.dictionary[e]
                        : e

        },
        m.component = class {
            constructor(e) {
                e && "object" == typeof e && Object.assign(this, e)
            }
        },
        m
}));
// # sourceMappingURL=/sm/672f3efbe81eace6cc7726b822dc12a38032537d7b964cc7a4e68df48624a959.map