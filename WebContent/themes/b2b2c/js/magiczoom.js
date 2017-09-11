(function () {
    if (window.magicJS) {
        return
    }
    var a = {
        version: "v2.5.1", UUID: 0, storage: {}, $uuid: function (c) {
            return (c.$J_UUID || (c.$J_UUID = ++$J.UUID))
        }, getStorage: function (c) {
            return ($J.storage[c] || ($J.storage[c] = {}))
        }, $F: function () {
        }, $false: function () {
            return false
        }, defined: function (c) {
            return (undefined != c)
        }, exists: function (c) {
            return !!(c)
        }, j1: function (c) {
            if (!$J.defined(c)) {
                return false
            }
            if (c.$J_TYPE) {
                return c.$J_TYPE
            }
            if (!!c.nodeType) {
                if (1 == c.nodeType) {
                    return "element"
                }
                if (3 == c.nodeType) {
                    return "textnode"
                }
            }
            if (c.length && c.item) {
                return "collection"
            }
            if (c.length && c.callee) {
                return "arguments"
            }
            if ((c instanceof window.Object || c instanceof window.Function) && c.constructor === $J.Class) {
                return "class"
            }
            if (c instanceof window.Array) {
                return "array"
            }
            if (c instanceof window.Function) {
                return "function"
            }
            if (c instanceof window.String) {
                return "string"
            }
            if ($J.j21.trident) {
                if ($J.defined(c.cancelBubble)) {
                    return "event"
                }
            } else {
                if (c === window.event || c.constructor == window.Event || c.constructor == window.MouseEvent || c.constructor == window.UIEvent || c.constructor == window.KeyboardEvent || c.constructor == window.KeyEvent) {
                    return "event"
                }
            }
            if (c instanceof window.Date) {
                return "date"
            }
            if (c instanceof window.RegExp) {
                return "regexp"
            }
            if (c === window) {
                return "window"
            }
            if (c === document) {
                return "document"
            }
            return typeof(c)
        }, extend: function (h, g) {
            if (!(h instanceof window.Array)) {
                h = [h]
            }
            for (var f = 0, d = h.length; f < d; f++) {
                if (!$J.defined(h)) {
                    continue
                }
                for (var e in(g || {})) {
                    try {
                        h[f][e] = g[e]
                    } catch (c) {
                    }
                }
            }
            return h[0]
        }, implement: function (g, f) {
            if (!(g instanceof window.Array)) {
                g = [g]
            }
            for (var e = 0, c = g.length; e < c; e++) {
                if (!$J.defined(g[e])) {
                    continue
                }
                if (!g[e].prototype) {
                    continue
                }
                for (var d in(f || {})) {
                    if (!g[e].prototype[d]) {
                        g[e].prototype[d] = f[d]
                    }
                }
            }
            return g[0]
        }, nativize: function (e, d) {
            if (!$J.defined(e)) {
                return e
            }
            for (var c in(d || {})) {
                if (!e[c]) {
                    e[c] = d[c]
                }
            }
            return e
        }, $try: function () {
            for (var d = 0, c = arguments.length; d < c; d++) {
                try {
                    return arguments[d]()
                } catch (f) {
                }
            }
            return null
        }, $A: function (e) {
            if (!$J.defined(e)) {
                return $mjs([])
            }
            if (e.toArray) {
                return $mjs(e.toArray())
            }
            if (e.item) {
                var d = e.length || 0, c = new Array(d);
                while (d--) {
                    c[d] = e[d]
                }
                return $mjs(c)
            }
            return $mjs(Array.prototype.slice.call(e))
        }, now: function () {
            return new Date().getTime()
        }, detach: function (g) {
            var e;
            switch ($J.j1(g)) {
                case"object":
                    e = {};
                    for (var f in g) {
                        e[f] = $J.detach(g[f])
                    }
                    break;
                case"array":
                    e = [];
                    for (var d = 0, c = g.length; d < c; d++) {
                        e[d] = $J.detach(g[d])
                    }
                    break;
                default:
                    return g
            }
            return $J.$(e)
        }, $: function (d) {
            if (!$J.defined(d)) {
                return null
            }
            if (d.$J_EXTENDED) {
                return d
            }
            switch ($J.j1(d)) {
                case"array":
                    d = $J.nativize(d, $J.extend($J.Array, {$J_EXTENDED: $J.$F}));
                    d.j14 = d.forEach;
                    return d;
                    break;
                case"string":
                    var c = document.getElementById(d);
                    if ($J.defined(c)) {
                        return $J.$(c)
                    }
                    return null;
                    break;
                case"window":
                case"document":
                    $J.$uuid(d);
                    d = $J.extend(d, $J.Doc);
                    break;
                case"element":
                    $J.$uuid(d);
                    d = $J.extend(d, $J.Element);
                    break;
                case"event":
                    d = $J.extend(d, $J.Event);
                    break;
                case"textnode":
                    return d;
                    break;
                case"function":
                case"array":
                case"date":
                default:
                    break
            }
            return $J.extend(d, {$J_EXTENDED: $J.$F})
        }, $new: function (c, e, d) {
            return $mjs($J.doc.createElement(c)).setProps(e || {}).j6(d || {})
        }
    };
    window.magicJS = window.$J = a;
    window.$mjs = a.$;
    $J.Array = {
        $J_TYPE: "array", indexOf: function (f, g) {
            var c = this.length;
            for (var d = this.length, e = (g < 0) ? Math.max(0, d + g) : g || 0; e < d; e++) {
                if (this[e] === f) {
                    return e
                }
            }
            return -1
        }, contains: function (c, d) {
            return this.indexOf(c, d) != -1
        }, forEach: function (c, f) {
            for (var e = 0, d = this.length; e < d; e++) {
                if (e in this) {
                    c.call(f, this[e], e, this)
                }
            }
        }, filter: function (c, h) {
            var g = [];
            for (var f = 0, d = this.length; f < d; f++) {
                if (f in this) {
                    var e = this[f];
                    if (c.call(h, this[f], f, this)) {
                        g.push(e)
                    }
                }
            }
            return g
        }, map: function (c, g) {
            var f = [];
            for (var e = 0, d = this.length; e < d; e++) {
                if (e in this) {
                    f[e] = c.call(g, this[e], e, this)
                }
            }
            return f
        }
    };
    $J.implement(String, {
        $J_TYPE: "string", j26: function () {
            return this.replace(/^\s+|\s+$/g, "")
        }, trimLeft: function () {
            return this.replace(/^\s+/g, "")
        }, trimRight: function () {
            return this.replace(/\s+$/g, "")
        }, j25: function (c) {
            return (this.toString() === c.toString())
        }, icompare: function (c) {
            return (this.toLowerCase().toString() === c.toLowerCase().toString())
        }, j22: function () {
            return this.replace(/-\D/g, function (c) {
                return c.charAt(1).toUpperCase()
            })
        }, dashize: function () {
            return this.replace(/[A-Z]/g, function (c) {
                return ("-" + c.charAt(0).toLowerCase())
            })
        }, j17: function (c) {
            return parseInt(this, c || 10)
        }, toFloat: function () {
            return parseFloat(this)
        }, j18: function () {
            return !this.replace(/true/i, "").j26()
        }, has: function (d, c) {
            c = c || "";
            return (c + this + c).indexOf(c + d + c) > -1
        }
    });
    a.implement(Function, {
        $J_TYPE: "function", j24: function () {
            var d = $J.$A(arguments), c = this, e = d.shift();
            return function () {
                return c.apply(e || null, d.concat($J.$A(arguments)))
            }
        }, j16: function () {
            var d = $J.$A(arguments), c = this, e = d.shift();
            return function (f) {
                return c.apply(e || null, $mjs([f || window.event]).concat(d))
            }
        }, j27: function () {
            var d = $J.$A(arguments), c = this, e = d.shift();
            return window.setTimeout(function () {
                return c.apply(c, d)
            }, e || 0)
        }, j28: function () {
            var d = $J.$A(arguments), c = this;
            return function () {
                return c.j27.apply(c, d)
            }
        }, interval: function () {
            var d = $J.$A(arguments), c = this, e = d.shift();
            return window.setInterval(function () {
                return c.apply(c, d)
            }, e || 0)
        }
    });
    var b = navigator.userAgent.toLowerCase();
    $J.j21 = {
        features: {xpath: !!(document.evaluate), air: !!(window.runtime), query: !!(document.querySelector)},
        engine: (window.opera) ? "presto" : !!(window.ActiveXObject) ? "trident" : (!navigator.taintEnabled) ? "webkit" : (undefined != document.getBoxObjectFor || null != window.mozInnerScreenY) ? "gecko" : "unknown",
        version: "",
        platform: b.match(/ip(?:ad|od|hone)/) ? "ios" : (b.match(/(?:webos|android)/) || navigator.platform.match(/mac|win|linux/i) || ["other"])[0].toLowerCase(),
        backCompat: document.compatMode && "backcompat" == document.compatMode.toLowerCase(),
        getDoc: function () {
            return (document.compatMode && "backcompat" == document.compatMode.toLowerCase()) ? document.body : document.documentElement
        },
        ready: false,
        onready: function () {
            if ($J.j21.ready) {
                return
            }
            $J.j21.ready = true;
            $J.body = $mjs(document.body);
            $J.win = $mjs(window);
            $mjs(document).raiseEvent("domready")
        }
    };
    $J.j21.touchScreen = $J.$A(["ios", "webos", "android"]).contains($J.j21.platform);
    (function () {
        function c() {
            return !!(arguments.callee.caller)
        }

        $J.j21.version = ("presto" == $J.j21.engine) ? !!(document.head) ? 270 : !!(window.applicationCache) ? 260 : !!(window.localStorage) ? 250 : ($J.j21.features.query) ? 220 : ((c()) ? 211 : ((document.getElementsByClassName) ? 210 : 200)) : ("trident" == $J.j21.engine) ? !!(window.msPerformance || window.performance) ? 900 : !!(window.XMLHttpRequest && window.postMessage) ? 6 : ((window.XMLHttpRequest) ? 5 : 4) : ("webkit" == $J.j21.engine) ? (($J.j21.features.xpath) ? (($J.j21.features.query) ? 525 : 420) : 419) : ("gecko" == $J.j21.engine) ? !!(document.head) ? 200 : !!document.readyState ? 192 : !!(window.localStorage) ? 191 : ((document.getElementsByClassName) ? 190 : 181) : "";
        $J.j21[$J.j21.engine] = $J.j21[$J.j21.engine + $J.j21.version] = true;
        if (window.chrome) {
            $J.j21.chrome = true
        }
    })();
    $J.Element = {
        j13: function (c) {
            return this.className.has(c, " ")
        }, j2: function (c) {
            if (c && !this.j13(c)) {
                this.className += (this.className ? " " : "") + c
            }
            return this
        }, j3: function (c) {
            c = c || ".*";
            this.className = this.className.replace(new RegExp("(^|\\s)" + c + "(?:\\s|$)"), "$1").j26();
            return this
        }, j4: function (c) {
            return this.j13(c) ? this.j3(c) : this.j2(c)
        }, j5: function (e) {
            e = (e == "float" && this.currentStyle) ? "styleFloat" : e.j22();
            var c = null, d = null;
            if (this.currentStyle) {
                c = this.currentStyle[e]
            } else {
                if (document.defaultView && document.defaultView.getComputedStyle) {
                    d = document.defaultView.getComputedStyle(this, null);
                    c = d ? d.getPropertyValue([e.dashize()]) : null
                }
            }
            if (!c) {
                c = this.style[e]
            }
            if ("opacity" == e) {
                return $J.defined(c) ? parseFloat(c) : 1
            }
            if (/^(border(Top|Bottom|Left|Right)Width)|((padding|margin)(Top|Bottom|Left|Right))$/.test(e)) {
                c = parseInt(c) ? c : "0px"
            }
            return ("auto" == c ? null : c)
        }, j6Prop: function (d, c) {
            try {
                if ("opacity" == d) {
                    this.j23(c);
                    return this
                }
                if ("float" == d) {
                    this.style[("undefined" === typeof(this.style.styleFloat)) ? "cssFloat" : "styleFloat"] = c;
                    return this
                }
                this.style[d.j22()] = c + (("number" == $J.j1(c) && !$mjs(["zIndex", "zoom"]).contains(d.j22())) ? "px" : "")
            } catch (f) {
            }
            return this
        }, j6: function (d) {
            for (var c in d) {
                this.j6Prop(c, d[c])
            }
            return this
        }, j19s: function () {
            var c = {};
            $J.$A(arguments).j14(function (d) {
                c[d] = this.j5(d)
            }, this);
            return c
        }, j23: function (g, d) {
            d = d || false;
            g = parseFloat(g);
            if (d) {
                if (g == 0) {
                    if ("hidden" != this.style.visibility) {
                        this.style.visibility = "hidden"
                    }
                } else {
                    if ("visible" != this.style.visibility) {
                        this.style.visibility = "visible"
                    }
                }
            }
            if ($J.j21.trident) {
                if (!this.currentStyle || !this.currentStyle.hasLayout) {
                    this.style.zoom = 1
                }
                try {
                    var e = this.filters.item("DXImageTransform.Microsoft.Alpha");
                    e.enabled = (1 != g);
                    e.opacity = g * 100
                } catch (c) {
                    this.style.filter += (1 == g) ? "" : "progid:DXImageTransform.Microsoft.Alpha(enabled=true,opacity=" + g * 100 + ")"
                }
            }
            this.style.opacity = g;
            return this
        }, setProps: function (c) {
            for (var d in c) {
                this.setAttribute(d, "" + c[d])
            }
            return this
        }, hide: function () {
            return this.j6({display: "none", visibility: "hidden"})
        }, show: function () {
            return this.j6({display: "block", visibility: "visible"})
        }, j7: function () {
            return {width: this.offsetWidth, height: this.offsetHeight}
        }, j10: function () {
            return {top: this.scrollTop, left: this.scrollLeft}
        }, j11: function () {
            var c = this, d = {top: 0, left: 0};
            do {
                d.left += c.scrollLeft || 0;
                d.top += c.scrollTop || 0;
                c = c.parentNode
            } while (c);
            return d
        }, j8: function () {
            if ($J.defined(document.documentElement.getBoundingClientRect)) {
                var c = this.getBoundingClientRect(), e = $mjs(document).j10(), g = $J.j21.getDoc();
                return {top: c.top + e.y - g.clientTop, left: c.left + e.x - g.clientLeft}
            }
            var f = this, d = t = 0;
            do {
                d += f.offsetLeft || 0;
                t += f.offsetTop || 0;
                f = f.offsetParent
            } while (f && !(/^(?:body|html)$/i).test(f.tagName));
            return {top: t, left: d}
        }, j9: function () {
            var d = this.j8();
            var c = this.j7();
            return {top: d.top, bottom: d.top + c.height, left: d.left, right: d.left + c.width}
        }, update: function (f) {
            try {
                this.innerHTML = f
            } catch (d) {
                this.innerText = f
            }
            return this
        }, j33: function () {
            return (this.parentNode) ? this.parentNode.removeChild(this) : this
        }, kill: function () {
            $J.$A(this.childNodes).j14(function (c) {
                if (3 == c.nodeType || 8 == c.nodeType) {
                    return
                }
                $mjs(c).kill()
            });
            this.j33();
            this.je3();
            if (this.$J_UUID) {
                $J.storage[this.$J_UUID] = null;
                delete $J.storage[this.$J_UUID]
            }
            return null
        }, append: function (e, d) {
            d = d || "bottom";
            var c = this.firstChild;
            ("top" == d && c) ? this.insertBefore(e, c) : this.appendChild(e);
            return this
        }, j32: function (e, d) {
            var c = $mjs(e).append(this, d);
            return this
        }, enclose: function (c) {
            this.append(c.parentNode.replaceChild(this, c));
            return this
        }, hasChild: function (c) {
            if (!(c = $mjs(c))) {
                return false
            }
            return (this == c) ? false : (this.contains && !($J.j21.webkit419)) ? (this.contains(c)) : (this.compareDocumentPosition) ? !!(this.compareDocumentPosition(c) & 16) : $J.$A(this.byTag(c.tagName)).contains(c)
        }
    };
    $J.Element.j19 = $J.Element.j5;
    $J.Element.j20 = $J.Element.j6;
    if (!window.Element) {
        window.Element = $J.$F;
        if ($J.j21.engine.webkit) {
            window.document.createElement("iframe")
        }
        window.Element.prototype = ($J.j21.engine.webkit) ? window["[[DOMElement.prototype]]"] : {}
    }
    $J.implement(window.Element, {$J_TYPE: "element"});
    $J.Doc = {
        j7: function () {
            if ($J.j21.presto925 || $J.j21.webkit419) {
                return {width: self.innerWidth, height: self.innerHeight}
            }
            return {width: $J.j21.getDoc().clientWidth, height: $J.j21.getDoc().clientHeight}
        }, j10: function () {
            return {x: self.pageXOffset || $J.j21.getDoc().scrollLeft, y: self.pageYOffset || $J.j21.getDoc().scrollTop}
        }, j12: function () {
            var c = this.j7();
            return {
                width: Math.max($J.j21.getDoc().scrollWidth, c.width),
                height: Math.max($J.j21.getDoc().scrollHeight, c.height)
            }
        }
    };
    $J.extend(document, {$J_TYPE: "document"});
    $J.extend(window, {$J_TYPE: "window"});
    $J.extend([$J.Element, $J.Doc], {
        j29: function (f, d) {
            var c = $J.getStorage(this.$J_UUID), e = c[f];
            if (undefined != d && undefined == e) {
                e = c[f] = d
            }
            return ($J.defined(e) ? e : null)
        }, j30: function (e, d) {
            var c = $J.getStorage(this.$J_UUID);
            c[e] = d;
            return this
        }, j31: function (d) {
            var c = $J.getStorage(this.$J_UUID);
            delete c[d];
            return this
        }
    });
    if (!(window.HTMLElement && window.HTMLElement.prototype && window.HTMLElement.prototype.getElementsByClassName)) {
        $J.extend([$J.Element, $J.Doc], {
            getElementsByClassName: function (c) {
                return $J.$A(this.getElementsByTagName("*")).filter(function (f) {
                    try {
                        return (1 == f.nodeType && f.className.has(c, " "))
                    } catch (d) {
                    }
                })
            }
        })
    }
    $J.extend([$J.Element, $J.Doc], {
        byClass: function () {
            return this.getElementsByClassName(arguments[0])
        }, byTag: function () {
            return this.getElementsByTagName(arguments[0])
        }
    });
    $J.Event = {
        $J_TYPE: "event", stop: function () {
            if (this.stopPropagation) {
                this.stopPropagation()
            } else {
                this.cancelBubble = true
            }
            if (this.preventDefault) {
                this.preventDefault()
            } else {
                this.returnValue = false
            }
            return this
        }, j15: function () {
            var d, c;
            d = ((/touch/i).test(this.type)) ? this.changedTouches[0] : this;
            return (!$J.defined(d)) ? {x: 0, y: 0} : {
                x: d.pageX || d.clientX + $J.j21.getDoc().scrollLeft,
                y: d.pageY || d.clientY + $J.j21.getDoc().scrollTop
            }
        }, getTarget: function () {
            var c = this.target || this.srcElement;
            while (c && 3 == c.nodeType) {
                c = c.parentNode
            }
            return c
        }, getRelated: function () {
            var d = null;
            switch (this.type) {
                case"mouseover":
                    d = this.relatedTarget || this.fromElement;
                    break;
                case"mouseout":
                    d = this.relatedTarget || this.toElement;
                    break;
                default:
                    return d
            }
            try {
                while (d && 3 == d.nodeType) {
                    d = d.parentNode
                }
            } catch (c) {
                d = null
            }
            return d
        }, getButton: function () {
            if (!this.which && this.button !== undefined) {
                return (this.button & 1 ? 1 : (this.button & 2 ? 3 : (this.button & 4 ? 2 : 0)))
            }
            return this.which
        }
    };
    $J._event_add_ = "addEventListener";
    $J._event_del_ = "removeEventListener";
    $J._event_prefix_ = "";
    if (!document.addEventListener) {
        $J._event_add_ = "attachEvent";
        $J._event_del_ = "detachEvent";
        $J._event_prefix_ = "on"
    }
    $J.extend([$J.Element, $J.Doc], {
        je1: function (f, e) {
            var h = ("domready" == f) ? false : true, d = this.j29("events", {});
            d[f] = d[f] || {};
            if (d[f].hasOwnProperty(e.$J_EUID)) {
                return this
            }
            if (!e.$J_EUID) {
                e.$J_EUID = Math.floor(Math.random() * $J.now())
            }
            var c = this, g = function (i) {
                return e.call(c)
            };
            if ("domready" == f) {
                if ($J.j21.ready) {
                    e.call(this);
                    return this
                }
            }
            if (h) {
                g = function (i) {
                    i = $J.extend(i || window.e, {$J_TYPE: "event"});
                    return e.call(c, $mjs(i))
                };
                this[$J._event_add_]($J._event_prefix_ + f, g, false)
            }
            d[f][e.$J_EUID] = g;
            return this
        }, je2: function (f) {
            var h = ("domready" == f) ? false : true, d = this.j29("events");
            if (!d || !d[f]) {
                return this
            }
            var g = d[f], e = arguments[1] || null;
            if (f && !e) {
                for (var c in g) {
                    if (!g.hasOwnProperty(c)) {
                        continue
                    }
                    this.je2(f, c)
                }
                return this
            }
            e = ("function" == $J.j1(e)) ? e.$J_EUID : e;
            if (!g.hasOwnProperty(e)) {
                return this
            }
            if ("domready" == f) {
                h = false
            }
            if (h) {
                this[$J._event_del_]($J._event_prefix_ + f, g[e], false)
            }
            delete g[e];
            return this
        }, raiseEvent: function (g, d) {
            var l = ("domready" == g) ? false : true, j = this, i;
            if (!l) {
                var f = this.j29("events");
                if (!f || !f[g]) {
                    return this
                }
                var h = f[g];
                for (var c in h) {
                    if (!h.hasOwnProperty(c)) {
                        continue
                    }
                    h[c].call(this)
                }
                return this
            }
            if (j === document && document.createEvent && !el.dispatchEvent) {
                j = document.documentElement
            }
            if (document.createEvent) {
                i = document.createEvent(g);
                i.initEvent(d, true, true)
            } else {
                i = document.createEventObject();
                i.eventType = g
            }
            if (document.createEvent) {
                j.dispatchEvent(i)
            } else {
                j.fireEvent("on" + d, i)
            }
            return i
        }, je3: function () {
            var c = this.j29("events");
            if (!c) {
                return this
            }
            for (var d in c) {
                this.je2(d)
            }
            this.j31("events");
            return this
        }
    });
    (function () {
        if ($J.j21.webkit && $J.j21.version < 420) {
            (function () {
                ($mjs(["loaded", "complete"]).contains(document.readyState)) ? $J.j21.onready() : arguments.callee.j27(50)
            })()
        } else {
            if ($J.j21.trident && window == top) {
                (function () {
                    ($J.$try(function () {
                        $J.j21.getDoc().doScroll("left");
                        return true
                    })) ? $J.j21.onready() : arguments.callee.j27(50)
                })()
            } else {
                $mjs(document).je1("DOMContentLoaded", $J.j21.onready);
                $mjs(window).je1("load", $J.j21.onready)
            }
        }
    })();
    $J.Class = function () {
        var g = null, d = $J.$A(arguments);
        if ("class" == $J.j1(d[0])) {
            g = d.shift()
        }
        var c = function () {
            for (var j in this) {
                this[j] = $J.detach(this[j])
            }
            if (this.constructor.$parent) {
                this.$parent = {};
                var n = this.constructor.$parent;
                for (var l in n) {
                    var i = n[l];
                    switch ($J.j1(i)) {
                        case"function":
                            this.$parent[l] = $J.Class.wrap(this, i);
                            break;
                        case"object":
                            this.$parent[l] = $J.detach(i);
                            break;
                        case"array":
                            this.$parent[l] = $J.detach(i);
                            break
                    }
                }
            }
            var h = (this.init) ? this.init.apply(this, arguments) : this;
            delete this.caller;
            return h
        };
        if (!c.prototype.init) {
            c.prototype.init = $J.$F
        }
        if (g) {
            var f = function () {
            };
            f.prototype = g.prototype;
            c.prototype = new f;
            c.$parent = {};
            for (var e in g.prototype) {
                c.$parent[e] = g.prototype[e]
            }
        } else {
            c.$parent = null
        }
        c.constructor = $J.Class;
        c.prototype.constructor = c;
        $J.extend(c.prototype, d[0]);
        $J.extend(c, {$J_TYPE: "class"});
        return c
    };
    a.Class.wrap = function (c, d) {
        return function () {
            var f = this.caller;
            var e = d.apply(c, arguments);
            return e
        }
    };
    $J.win = $mjs(window);
    $J.doc = $mjs(document)
})();
(function (a) {
    if (!a) {
        throw"MagicJS not found";
        return
    }
    if (a.FX) {
        return
    }
    var b = a.$;
    a.FX = new a.Class({
        options: {
            fps: 50, duration: 500, transition: function (c) {
                return -(Math.cos(Math.PI * c) - 1) / 2
            }, onStart: a.$F, onComplete: a.$F, onBeforeRender: a.$F, roundCss: true
        }, styles: null, init: function (d, c) {
            this.el = $mjs(d);
            this.options = a.extend(this.options, c);
            this.timer = false
        }, start: function (c) {
            this.styles = c;
            this.state = 0;
            this.curFrame = 0;
            this.startTime = a.now();
            this.finishTime = this.startTime + this.options.duration;
            this.timer = this.loop.j24(this).interval(Math.round(1000 / this.options.fps));
            this.options.onStart.call();
            return this
        }, stop: function (c) {
            c = a.defined(c) ? c : false;
            if (this.timer) {
                clearInterval(this.timer);
                this.timer = false
            }
            if (c) {
                this.render(1);
                this.options.onComplete.j27(10)
            }
            return this
        }, calc: function (e, d, c) {
            return (d - e) * c + e
        }, loop: function () {
            var d = a.now();
            if (d >= this.finishTime) {
                if (this.timer) {
                    clearInterval(this.timer);
                    this.timer = false
                }
                this.render(1);
                this.options.onComplete.j27(10);
                return this
            }
            var c = this.options.transition((d - this.startTime) / this.options.duration);
            this.render(c)
        }, render: function (c) {
            var d = {};
            for (var e in this.styles) {
                if ("opacity" === e) {
                    d[e] = Math.round(this.calc(this.styles[e][0], this.styles[e][1], c) * 100) / 100
                } else {
                    d[e] = this.calc(this.styles[e][0], this.styles[e][1], c);
                    if (this.options.roundCss) {
                        d[e] = Math.round(d[e])
                    }
                }
            }
            this.options.onBeforeRender(d);
            this.set(d)
        }, set: function (c) {
            return this.el.j6(c)
        }
    });
    a.FX.Transition = {
        linear: function (c) {
            return c
        }, sineIn: function (c) {
            return -(Math.cos(Math.PI * c) - 1) / 2
        }, sineOut: function (c) {
            return 1 - a.FX.Transition.sineIn(1 - c)
        }, expoIn: function (c) {
            return Math.pow(2, 8 * (c - 1))
        }, expoOut: function (c) {
            return 1 - a.FX.Transition.expoIn(1 - c)
        }, quadIn: function (c) {
            return Math.pow(c, 2)
        }, quadOut: function (c) {
            return 1 - a.FX.Transition.quadIn(1 - c)
        }, cubicIn: function (c) {
            return Math.pow(c, 3)
        }, cubicOut: function (c) {
            return 1 - a.FX.Transition.cubicIn(1 - c)
        }, backIn: function (d, c) {
            c = c || 1.618;
            return Math.pow(d, 2) * ((c + 1) * d - c)
        }, backOut: function (d, c) {
            return 1 - a.FX.Transition.backIn(1 - d)
        }, elasticIn: function (d, c) {
            c = c || [];
            return Math.pow(2, 10 * --d) * Math.cos(20 * d * Math.PI * (c[0] || 1) / 3)
        }, elasticOut: function (d, c) {
            return 1 - a.FX.Transition.elasticIn(1 - d, c)
        }, bounceIn: function (e) {
            for (var d = 0, c = 1; 1; d += c, c /= 2) {
                if (e >= (7 - 4 * d) / 11) {
                    return c * c - Math.pow((11 - 6 * d - 11 * e) / 4, 2)
                }
            }
        }, bounceOut: function (c) {
            return 1 - a.FX.Transition.bounceIn(1 - c)
        }, none: function (c) {
            return 0
        }
    }
})(magicJS);
(function (a) {
    if (!a) {
        throw"MagicJS not found";
        return
    }
    if (a.PFX) {
        return
    }
    var b = a.$;
    a.PFX = new a.Class(a.FX, {
        init: function (c, d) {
            this.el_arr = c;
            this.options = a.extend(this.options, d);
            this.timer = false
        }, start: function (c) {
            this.$parent.start([]);
            this.styles_arr = c;
            return this
        }, render: function (c) {
            for (var d = 0; d < this.el_arr.length; d++) {
                this.el = $mjs(this.el_arr[d]);
                this.styles = this.styles_arr[d];
                this.$parent.render(c)
            }
        }
    })
})(magicJS);
var MagicZoom = (function (c) {
    var d = c.$;
    c.$Ff = function (f) {
        $mjs(f).stop();
        return false
    };
    var a = {
        version: "v4.0.1",
        options: {},
        defaults: {
            opacity: 50,
            opacityReverse: false,
            smoothingSpeed: 40,
            fps: 25,
            zoomWidth: 300,
            zoomHeight: 300,
            zoomDistance: 15,
            zoomPosition: "right",
            zoomAlign: "top",
            zoomWindowEffect: "shadow",
            dragMode: false,
            moveOnClick: true,
            alwaysShowZoom: false,
            preservePosition: false,
            x: -1,
            y: -1,
            clickToActivate: false,
            clickToDeactivate: false,
            initializeOn: "load",
            smoothing: true,
            showTitle: "top",
            titleSource: "title",
            zoomFade: true,
            zoomFadeInSpeed: 400,
            zoomFadeOutSpeed: 200,
            hotspots: "",
            hint: true,
            hintText: "Zoom",
            hintPosition: "tl",
            hintOpacity: 75,
            hintClass: "MagicZoomHint",
            showLoading: true,
            loadingMsg: "Loading zoom..",
            loadingOpacity: 75,
            loadingPositionX: -1,
            loadingPositionY: -1,
            selectorsChange: "click",
            selectorsMouseoverDelay: 60,
            selectorsEffect: "dissolve",
            selectorsEffectSpeed: 400,
            preloadSelectorsSmall: true,
            preloadSelectorsBig: false,
            selectorsClass: "",
            fitZoomWindow: true,
            entireImage: false,
            rightClick: false,
            disableZoom: false
        },
        z39: $mjs([/^(opacity)(\s+)?:(\s+)?(\d+)$/i, /^(opacity-reverse)(\s+)?:(\s+)?(true|false)$/i, /^(smoothing\-speed)(\s+)?:(\s+)?(\d+)$/i, /^(fps)(\s+)?:(\s+)?(\d+)$/i, /^(zoom\-width)(\s+)?:(\s+)?(\d+)(px)?/i, /^(zoom\-height)(\s+)?:(\s+)?(\d+)(px)?/i, /^(zoom\-distance)(\s+)?:(\s+)?(\d+)(px)?/i, /^(zoom\-position)(\s+)?:(\s+)?(right|left|top|bottom|custom|inner|#([a-z0-9_\-:\.]+))$/i, /^(zoom\-align)(\s+)?:(\s+)?(right|left|top|bottom|center)$/i, /^(zoom\-window\-effect)(\s+)?:(\s+)?(shadow|glow|false)$/i, /^(drag\-mode)(\s+)?:(\s+)?(true|false)$/i, /^(move\-on\-click)(\s+)?:(\s+)?(true|false)$/i, /^(always\-show\-zoom)(\s+)?:(\s+)?(true|false)$/i, /^(preserve\-position)(\s+)?:(\s+)?(true|false)$/i, /^(x)(\s+)?:(\s+)?([\d.]+)(px)?/i, /^(y)(\s+)?:(\s+)?([\d.]+)(px)?/i, /^(click\-to\-activate)(\s+)?:(\s+)?(true|false)$/i, /^(click\-to\-deactivate)(\s+)?:(\s+)?(true|false)$/i, /^(initialize\-on)(\s+)?:(\s+)?(load|click|mouseover)$/i, /^(click\-to\-initialize)(\s+)?:(\s+)?(true|false)$/i, /^(smoothing)(\s+)?:(\s+)?(true|false)$/i, /^(show\-title)(\s+)?:(\s+)?(true|false|top|bottom)$/i, /^(title\-source)(\s+)?:(\s+)?(title|#([a-z0-9_\-:\.]+))$/i, /^(zoom\-fade)(\s+)?:(\s+)?(true|false)$/i, /^(zoom\-fade\-in\-speed)(\s+)?:(\s+)?(\d+)$/i, /^(zoom\-fade\-out\-speed)(\s+)?:(\s+)?(\d+)$/i, /^(hotspots)(\s+)?:(\s+)?([a-z0-9_\-:\.]+)$/i, /^(hint)(\s+)?:(\s+)?(true|false)/i, /^(hint\-text)(\s+)?:(\s+)?([^;]*)$/i, /^(hint\-opacity)(\s+)?:(\s+)?(\d+)$/i, /^(hint\-position)(\s+)?:(\s+)?(tl|tr|tc|bl|br|bc)/i, /^(show\-loading)(\s+)?:(\s+)?(true|false)$/i, /^(loading\-msg)(\s+)?:(\s+)?([^;]*)$/i, /^(loading\-opacity)(\s+)?:(\s+)?(\d+)$/i, /^(loading\-position\-x)(\s+)?:(\s+)?(\d+)(px)?/i, /^(loading\-position\-y)(\s+)?:(\s+)?(\d+)(px)?/i, /^(thumb\-change)(\s+)?:(\s+)?(click|mouseover)$/i, /^(selectors\-change)(\s+)?:(\s+)?(click|mouseover)$/i, /^(selectors\-mouseover\-delay)(\s+)?:(\s+)?(\d+)$/i, /^(selectors\-effect)(\s+)?:(\s+)?(dissolve|fade|pounce|false)$/i, /^(selectors\-effect\-speed)(\s+)?:(\s+)?(\d+)$/i, /^(selectors\-class)(\s+)?:(\s+)?([a-z0-9_\-:\.]+)$/i, /^(fit\-zoom\-window)(\s+)?:(\s+)?(true|false)$/i, /^(preload\-selectors\-small)(\s+)?:(\s+)?(true|false)$/i, /^(preload\-selectors\-big)(\s+)?:(\s+)?(true|false)$/i, /^(entire\-image)(\s+)?:(\s+)?(true|false)$/i, /^(right\-click)(\s+)?:(\s+)?(true|false)$/i, /^(disable\-zoom)(\s+)?:(\s+)?(true|false)$/i]),
        zooms: $mjs([]),
        z8: function (h) {
            var g = /(click|mouseover)/i;
            for (var f = 0; f < a.zooms.length; f++) {
                if (a.zooms[f].z30 && !a.zooms[f].activatedEx) {
                    a.zooms[f].pause()
                } else {
                    if (g.test(a.zooms[f].options.initializeOn) && a.zooms[f].initMouseEvent) {
                        a.zooms[f].initMouseEvent = h
                    }
                }
            }
        },
        stop: function (f) {
            var e = $mjs([]);
            if (f) {
                if ((f = $mjs(f)) && f.zoom) {
                    e.push(f)
                } else {
                    return false
                }
            } else {
                e = $mjs(c.$A(c.body.byTag("A")).filter(function (g) {
                    return ((" " + g.className + " ").match(/\sMagicZoom\s/) && g.zoom)
                }))
            }
            e.j14(function (g) {
                g.zoom && g.zoom.stop()
            }, this)
        },
        start: function (e) {
            if (0 == arguments.length) {
                a.refresh();
                return true
            }
            e = $mjs(e);
            if (!e || !(" " + e.className + " ").match(/\s(MagicZoom|MagicZoomPlus)\s/)) {
                return false
            }
            if (!e.zoom) {
                var f = null;
                while (f = e.firstChild) {
                    if (f.tagName == "IMG") {
                        break
                    }
                    e.removeChild(f)
                }
                while (f = e.lastChild) {
                    if (f.tagName == "IMG") {
                        break
                    }
                    e.removeChild(f)
                }
                if (!e.firstChild || e.firstChild.tagName != "IMG") {
                    throw"Invalid Magic Zoom"
                }
                a.zooms.push(new a.zoom(e, (arguments.length > 1) ? arguments[1] : undefined))
            } else {
                e.zoom.start()
            }
        },
        update: function (h, e, g, f) {
            if ((h = $mjs(h)) && h.zoom) {
                h.zoom.update(e, g, f);
                return true
            }
            return false
        },
        refresh: function () {
            c.$A(window.document.getElementsByTagName("A")).j14(function (e) {
                if (e.className.has("MagicZoom", " ")) {
                    if (a.stop(e)) {
                        a.start.j27(100, e)
                    } else {
                        a.start(e)
                    }
                }
            }, this)
        },
        show: function (e) {
            if ((e = $mjs(e)) && e.zoom) {
                return e.zoom.activate()
            }
            return false
        },
        getXY: function (e) {
            if ((e = $mjs(e)) && e.zoom) {
                return {x: e.zoom.options.x, y: e.zoom.options.y}
            }
        },
        x7: function (g) {
            var f, e;
            f = "";
            for (e = 0; e < g.length; e++) {
                f += String.fromCharCode(14 ^ g.charCodeAt(e))
            }
            return f
        }
    };
    a.z48 = function () {
        this.init.apply(this, arguments)
    };
    a.z48.prototype = {
        init: function (e) {
            this.cb = null;
            this.z9 = null;
            this.onErrorHandler = this.onError.j16(this);
            this.z10 = null;
            this.width = 0;
            this.height = 0;
            this.border = {left: 0, right: 0, top: 0, bottom: 0};
            this.padding = {left: 0, right: 0, top: 0, bottom: 0};
            this.ready = false;
            this._tmpp = null;
            if ("string" == c.j1(e)) {
                this._tmpp = c.$new("div").j6({
                    position: "absolute",
                    top: "-10000px",
                    width: "1px",
                    height: "1px",
                    overflow: "hidden"
                }).j32(c.body);
                this.self = c.$new("img").j32(this._tmpp);
                this.z11();
                this.self.src = e
            } else {
                this.self = $mjs(e);
                this.z11();
                this.self.src = e.src
            }
        }, _cleanup: function () {
            if (this._tmpp) {
                if (this.self.parentNode == this._tmpp) {
                    this.self.j33().j6({position: "static", top: "auto"})
                }
                this._tmpp.kill();
                this._tmpp = null
            }
        }, onError: function (f) {
            if (f) {
                $mjs(f).stop()
            }
            if (this.cb) {
                this._cleanup();
                this.cb.call(this, false)
            }
            this.unload()
        }, z11: function (e) {
            this.z9 = null;
            if (e == true || !(this.self.src && (this.self.complete || this.self.readyState == "complete"))) {
                this.z9 = function (f) {
                    if (f) {
                        $mjs(f).stop()
                    }
                    if (this.ready) {
                        return
                    }
                    this.ready = true;
                    this.z13();
                    if (this.cb) {
                        this._cleanup();
                        this.cb.call()
                    }
                }.j16(this);
                this.self.je1("load", this.z9);
                $mjs(["abort", "error"]).j14(function (f) {
                    this.self.je1(f, this.onErrorHandler)
                }, this)
            } else {
                this.ready = true
            }
        }, update: function (f) {
            this.unload();
            var e = c.$new("a", {href: f});
            if (this.self.src.has(e.href)) {
                this.ready = true
            } else {
                this.z11(true);
                this.self.src = f
            }
            e = null
        }, z13: function () {
            this.width = this.self.width;
            this.height = this.self.height;
            if (this.width == 0 && this.height == 0 && c.j21.webkit) {
                this.width = this.self.naturalWidth;
                this.height = this.self.naturalHeight
            }
            $mjs(["Left", "Right", "Top", "Bottom"]).j14(function (f) {
                this.padding[f.toLowerCase()] = this.self.j19("padding" + f).j17();
                this.border[f.toLowerCase()] = this.self.j19("border" + f + "Width").j17()
            }, this);
            if (c.j21.presto || (c.j21.trident && !c.j21.backCompat)) {
                this.width -= this.padding.left + this.padding.right;
                this.height -= this.padding.top + this.padding.bottom
            }
        }, getBox: function () {
            var e = null;
            e = this.self.j9();
            return {
                top: e.top + this.border.top,
                bottom: e.bottom - this.border.bottom,
                left: e.left + this.border.left,
                right: e.right - this.border.right
            }
        }, z12: function () {
            if (this.z10) {
                this.z10.src = this.self.src;
                this.self = null;
                this.self = this.z10
            }
        }, load: function (e) {
            if (this.ready) {
                if (!this.width) {
                    this.z13()
                }
                this._cleanup();
                e.call()
            } else {
                this.cb = e
            }
        }, unload: function () {
            if (this.z9) {
                this.self.je2("load", this.z9)
            }
            $mjs(["abort", "error"]).j14(function (e) {
                this.self.je2(e, this.onErrorHandler)
            }, this);
            this.z9 = null;
            this.cb = null;
            this.width = null;
            this.ready = false;
            this._new = false
        }
    };
    a.zoom = function () {
        this.construct.apply(this, arguments)
    };
    a.zoom.prototype = {
        construct: function (g, f) {
            var e = {};
            this.z28 = -1;
            this.z30 = false;
            this.ddx = 0;
            this.ddy = 0;
            this.activatedEx = false;
            this.z44 = null;
            this.options = c.detach(a.defaults);
            if (g) {
                this.c = $mjs(g)
            }
            this.divTag = ("div" == this.c.tagName.toLowerCase());
            e = c.extend(e, this.z37());
            e = c.extend(e, this.z37(this.c.rel));
            if (f) {
                e = c.extend(e, this.z37(f))
            }
            if (e.dragMode && undefined === e.alwaysShowZoom) {
                e.alwaysShowZoom = true
            }
            c.extend(this.options, e);
            if ("load" == this.options.initializeOn && c.defined(this.options.clickToInitialize) && "true" == this.options.clickToInitialize.toString()) {
                this.options.initializeOn = "click"
            }
            if (c.defined(this.options.thumbChange) && this.options.thumbChange != this.options.selectorsChange) {
                this.options.selectorsChange = this.options.thumbChange
            }
            if (c.j21.touchScreen) {
                this.options.selectorsChange = "click";
                this.options.initializeOn = ("mouseover" == this.options.initializeOn) ? "click" : this.options.initializeOn;
                this.options.clickToDeactivate = false;
                if (window.screen.height <= 480) {
                    this.options.zoomPosition = "inner"
                }
            }
            if (this.options.disableZoom) {
                this.z30 = false;
                this.options.clickToActivate = true;
                this.options.hint = false
            }
            if (g) {
                this.lastSelector = null;
                this.z14 = this.mousedown.j16(this);
                this.z15 = this.mouseup.j16(this);
                this.z16 = this.show.j24(this, false);
                this.z17 = this.z29.j24(this);
                this.z43Bind = this.z43.j16(this);
                if (c.j21.touchScreen) {
                    this.c.je1("touchstart", this.z14);
                    this.c.je1("touchend", this.z15)
                } else {
                    if (!this.divTag) {
                        this.c.je1("click", function (i) {
                            var h = i.getButton();
                            if (3 == h) {
                                return true
                            }
                            $mjs(i).stop();
                            if (!c.j21.trident) {
                                this.blur()
                            }
                            return false
                        })
                    }
                    this.c.je1("mousedown", this.z14);
                    this.c.je1("mouseup", this.z15);
                    if ("mouseover" == this.options.initializeOn) {
                        this.c.je1("mouseover", this.z14)
                    }
                }
                this.c.unselectable = "on";
                this.c.style.MozUserSelect = "none";
                this.c.je1("selectstart", c.$Ff);
                if (!this.divTag) {
                    this.c.j6({
                        position: "relative",
                        display: "inline-block",
                        textDecoration: "none",
                        outline: "0",
                        cursor: "hand"
                    });
                    if (c.j21.gecko181 || c.j21.presto) {
                        this.c.j6({display: "block"})
                    }
                    if (this.c.j5("textAlign") == "center") {
                        this.c.j6({margin: "auto auto"})
                    }
                }
                this.c.zoom = this
            } else {
                this.options.initializeOn = "load"
            }
            if (!this.options.rightClick) {
                this.c.je1("contextmenu", c.$Ff)
            }
            if ("load" == this.options.initializeOn) {
                this.z18()
            } else {
                if ("" != this.c.id) {
                    this.z26(true)
                }
            }
        }, z18: function () {
            var j, m, l, k, h;
            h = ["^bko}k.{~i|ojk.za.h{bb.xk|}ga`.ah.Coigm.Taac(-6:6<5", "#ff0000", 10, "bold", "center", "100%"];
            if (!this.z7) {
                this.z7 = new a.z48(this.c.firstChild);
                this.z1 = new a.z48(this.c.href)
            } else {
                this.z1.update(this.c.href)
            }
            if (!this.z47) {
                this.z47 = {
                    self: $mjs(document.createElement("DIV"))[(this.divTag) ? "j3" : "j2"]("MagicZoomBigImageCont").j6({
                        overflow: "hidden",
                        zIndex: 100,
                        top: "-100000px",
                        position: "absolute",
                        width: this.options.zoomWidth + "px",
                        height: this.options.zoomHeight + "px"
                    }), zoom: this, z21: "0px", initTopPos: 0, initLeftPos: 0
                };
                switch (this.options.zoomWindowEffect) {
                    case"shadow":
                        this.z47.self.j2("MagicBoxShadow");
                        break;
                    case"glow":
                        this.z47.self.j2("MagicBoxGlow");
                        break;
                    default:
                        break
                }
                this.z47.hide = function () {
                    if (this.self.style.top != "-100000px" && this.zoom.z4 && !this.zoom.z4.z38) {
                        this.z21 = this.self.style.top;
                        this.self.style.top = "-100000px"
                    }
                };
                this.z47.z22 = this.z47.hide.j24(this.z47);
                if (c.j21.trident4) {
                    j = $mjs(document.createElement("IFRAME"));
                    j.src = "javascript:''";
                    j.j6({left: "0px", top: "0px", position: "absolute", "z-index": -1}).frameBorder = 0;
                    this.z47.z23 = this.z47.self.appendChild(j)
                }
                this.z47.z41 = $mjs(document.createElement("DIV")).j2("MagicZoomHeader").j6({
                    position: "relative",
                    zIndex: 10,
                    left: "0px",
                    top: "0px",
                    padding: "3px"
                }).hide();
                m = document.createElement("DIV");
                m.style.overflow = "hidden";
                m.appendChild(this.z1.self);
                this.z1.self.j6({padding: "0px", margin: "0px", border: "0px", width: "auto", height: "auto"});
                if (this.options.showTitle == "bottom") {
                    this.z47.self.appendChild(m);
                    this.z47.self.appendChild(this.z47.z41)
                } else {
                    this.z47.self.appendChild(this.z47.z41);
                    this.z47.self.appendChild(m)
                }
                if (this.options.zoomPosition == "custom" && $mjs(this.c.id + "-big")) {
                    $mjs(this.c.id + "-big").appendChild(this.z47.self)
                } else {
                    if (this.options.zoomPosition.has("#")) {
                        var n = this.options.zoomPosition.replace(/^#/, "");
                        if ($mjs(n)) {
                            $mjs(n).appendChild(this.z47.self)
                        }
                    } else {
                        this.c.appendChild(this.z47.self)
                    }
                }
                if ("undefined" !== typeof(h)) {
                    this.z47.g = $mjs(document.createElement("div")).j6({
                        color: h[1],
                        fontSize: h[2] + "px",
                        fontWeight: h[3],
                        fontFamily: "Tahoma",
                        position: "absolute",
                        width: h[5],
                        textAlign: h[4],
                        left: "0px"
                    });
                    this.z47.self.appendChild(this.z47.g)
                }
            }
            if (this.options.showTitle != "false" && this.options.showTitle != false) {
                var i = this.z47.z41;
                i.hide();
                while (l = i.firstChild) {
                    i.removeChild(l)
                }
                if (this.options.titleSource == "title" && "" != this.c.title) {
                    i.appendChild(document.createTextNode(this.c.title));
                    i.show()
                } else {
                    if (this.options.titleSource.has("#")) {
                        var n = this.options.titleSource.replace(/^#/, "");
                        if ($mjs(n)) {
                            i.update($mjs(n).innerHTML);
                            i.show()
                        }
                    }
                }
            } else {
                this.z47.z41.hide()
            }
            this.c.z46 = this.c.title;
            this.c.title = "";
            this.z7.load(this.z19.j24(this))
        }, z19: function (e) {
            if (!e && e !== undefined) {
                return
            }
            if (!this.options.opacityReverse) {
                this.z7.self.j23(1)
            }
            if (!this.divTag) {
                this.c.j6({width: this.z7.width + "px"})
            }
            if (this.options.showLoading) {
                this.z24 = setTimeout(this.z17, 400)
            }
            if (this.options.hotspots != "" && $mjs(this.options.hotspots)) {
                this.z25()
            }
            if (this.c.id != "") {
                this.z26()
            }
            this.z1.load(this.z20.j24(this))
        }, z20: function (g) {
            var f, e;
            if (!g && g !== undefined) {
                clearTimeout(this.z24);
                if (this.options.showLoading && this.z3) {
                    this.z3.hide()
                }
                return
            }
            e = this.z7.self.j9();
            if (e.bottom == e.top) {
                this.z20.j24(this).j27(500);
                return
            }
            if (this.z7.width == 0 && c.j21.trident) {
                this.z7.z13();
                this.z1.z13()
            }
            f = this.z47.z41.j7();
            if (this.options.fitZoomWindow || this.options.entireImage) {
                if ((this.z1.width < this.options.zoomWidth) || this.options.entireImage) {
                    this.options.zoomWidth = this.z1.width;
                    this.z47.self.j6({width: this.options.zoomWidth});
                    f = this.z47.z41.j7()
                }
                if ((this.z1.height < this.options.zoomHeight) || this.options.entireImage) {
                    this.options.zoomHeight = this.z1.height + f.height
                }
            }
            switch (this.options.zoomPosition) {
                case"custom":
                    break;
                case"right":
                    this.z47.self.style.left = e.right - e.left + this.options.zoomDistance + "px";
                    break;
                case"left":
                    this.z47.self.style.left = "-" + (this.options.zoomDistance + this.options.zoomWidth) + "px";
                    break;
                case"top":
                    this.z47.z21 = "-" + (this.options.zoomDistance + this.options.zoomHeight) + "px";
                    break;
                case"bottom":
                    this.z47.z21 = e.bottom - e.top + this.options.zoomDistance + "px";
                    break;
                case"inner":
                    this.z47.self.j6({left: "0px", height: this.z7.height + "px", width: this.z7.width + "px"});
                    this.options.zoomWidth = this.z7.width;
                    this.options.zoomHeight = this.z7.height;
                    this.z47.z21 = "0px";
                    break
            }
            if (this.options.showTitle == "bottom") {
                this.z1.self.parentNode.style.height = (this.options.zoomHeight - f.height) + "px"
            }
            this.z47.self.j6({height: this.options.zoomHeight + "px", width: this.options.zoomWidth + "px"}).j23(1);
            if (c.j21.trident4 && this.z47.z23) {
                this.z47.z23.j6({width: this.options.zoomWidth + "px", height: this.options.zoomHeight + "px"})
            }
            if (this.options.zoomPosition == "right" || this.options.zoomPosition == "left") {
                if (this.options.zoomAlign == "center") {
                    this.z47.z21 = -1 * (this.options.zoomHeight - e.bottom + e.top) / 2 + "px"
                } else {
                    if (this.options.zoomAlign == "bottom") {
                        this.z47.z21 = -1 * (this.options.zoomHeight - e.bottom + e.top) + "px"
                    } else {
                        this.z47.z21 = "0px"
                    }
                }
            } else {
                if (this.options.zoomPosition == "top" || this.options.zoomPosition == "bottom") {
                    if (this.options.zoomAlign == "center") {
                        this.z47.self.style.left = -1 * (this.options.zoomWidth - e.right + e.left) / 2 + "px"
                    } else {
                        if (this.options.zoomAlign == "right") {
                            this.z47.self.style.left = -1 * (this.options.zoomWidth - e.right + e.left) + "px"
                        } else {
                            this.z47.self.style.left = "0px"
                        }
                    }
                }
            }
            this.z47.initTopPos = parseInt(this.z47.z21, 10);
            this.z47.initLeftPos = parseInt(this.z47.self.style.left, 10);
            this.zoomViewHeight = this.options.zoomHeight - f.height;
            if (this.z47.g) {
                this.z47.g.j6({top: this.options.showTitle == "bottom" ? "0px" : ((this.options.zoomHeight - 20) + "px")})
            }
            this.z1.self.j6({position: "relative", borderWidth: "0px", padding: "0px", left: "0px", top: "0px"});
            this.z27();
            if (this.options.alwaysShowZoom) {
                if (this.options.x == -1) {
                    this.options.x = this.z7.width / 2
                }
                if (this.options.y == -1) {
                    this.options.y = this.z7.height / 2
                }
                this.show()
            } else {
                if (this.options.zoomFade) {
                    this.z2 = new c.FX(this.z47.self)
                }
                this.z47.self.j6({top: "-100000px"})
            }
            if (this.options.showLoading && this.z3) {
                this.z3.hide()
            }
            if (c.j21.touchScreen) {
                this.c.je1("touchstart", this.z43Bind);
                this.c.je1("touchmove", this.z43Bind);
                this.c.je1("touchend", this.z43Bind)
            } else {
                this.c.je1("mousemove", this.z43Bind);
                this.c.je1("mouseout", this.z43Bind)
            }
            this.setupHint();
            if (!this.options.disableZoom && (!this.options.clickToActivate || "click" == this.options.initializeOn)) {
                this.z30 = true
            }
            if ("click" == this.options.initializeOn && this.initMouseEvent) {
                this.z43(this.initMouseEvent)
            }
            if (this.activatedEx) {
                this.activate()
            }
            this.z28 = c.now()
        }, setupHint: function () {
            var i = /tr|br/i, e = /bl|br|bc/i, f = /bc|tc/i, h = null;
            this.hintVisible = undefined;
            if (!this.options.hint) {
                if (this.hint) {
                    this.hint.kill();
                    this.hint = undefined
                }
                return
            }
            if (!this.hint) {
                this.hint = $mjs(document.createElement("DIV")).j2(this.options.hintClass).j6({
                    display: "block",
                    overflow: "hidden",
                    position: "absolute",
                    visibility: "hidden",
                    "z-index": 1
                });
                if (this.options.hintText != "") {
                    this.hint.appendChild(document.createTextNode(this.options.hintText))
                }
                this.c.appendChild(this.hint)
            } else {
                if (this.options.hintText != "") {
                    h = this.hint[(this.hint.firstChild) ? "replaceChild" : "appendChild"](document.createTextNode(this.options.hintText), this.hint.firstChild);
                    h = null
                }
            }
            this.hint.j6({
                left: "auto",
                right: "auto",
                top: "auto",
                bottom: "auto",
                display: "block",
                opacity: (this.options.hintOpacity / 100),
                "max-width": (this.z7.width - 4)
            });
            var g = this.hint.j7();
            this.hint.j6Prop((i.test(this.options.hintPosition) ? "right" : "left"), (f.test(this.options.hintPosition) ? (this.z7.width - g.width) / 2 : 2)).j6Prop((e.test(this.options.hintPosition) ? "bottom" : "top"), 2);
            this.hintVisible = true;
            this.hint.show()
        }, z29: function () {
            if (this.z1.ready) {
                return
            }
            this.z3 = $mjs(document.createElement("DIV")).j2("MagicZoomLoading").j23(this.options.loadingOpacity / 100).j6({
                display: "block",
                overflow: "hidden",
                position: "absolute",
                visibility: "hidden",
                "z-index": 20,
                "max-width": (this.z7.width - 4)
            });
            this.z3.appendChild(document.createTextNode(this.options.loadingMsg));
            this.c.appendChild(this.z3);
            var e = this.z3.j7();
            this.z3.j6({
                left: (this.options.loadingPositionX == -1 ? ((this.z7.width - e.width) / 2) : (this.options.loadingPositionX)) + "px",
                top: (this.options.loadingPositionY == -1 ? ((this.z7.height - e.height) / 2) : (this.options.loadingPositionY)) + "px"
            });
            this.z3.show()
        }, z26: function (g) {
            var e, h, f = new RegExp("zoom\\-id(\\s+)?:(\\s+)?" + this.c.id + "($|;)");
            this.selectors = $mjs([]);
            c.$A(document.getElementsByTagName("A")).j14(function (j) {
                if (f.test(j.rel)) {
                    if (!$mjs(j).z36) {
                        j.z36 = function (k) {
                            if (!c.j21.trident) {
                                this.blur()
                            }
                            $mjs(k).stop();
                            return false
                        };
                        j.je1("click", j.z36)
                    }
                    if (g) {
                        return
                    }
                    var i = c.$new("a", {href: j.rev});
                    if (this.options.selectorsClass != "" && this.z1.self.src.has(j.href) && this.z7.self.src.has(i.href)) {
                        $mjs(j).j2(this.options.selectorsClass)
                    }
                    i = null;
                    if (!j.z34) {
                        j.z34 = function (l, k) {
                            if (k.hasChild(l.getRelated())) {
                                return
                            }
                            if (l.type == "mouseout") {
                                if (this.z35) {
                                    clearTimeout(this.z35)
                                }
                                this.z35 = false;
                                return
                            }
                            if (k.title != "") {
                                this.c.title = k.title
                            }
                            if (l.type == "mouseover") {
                                this.z35 = setTimeout(this.update.j24(this, k.href, k.rev, k.rel, k), this.options.selectorsMouseoverDelay)
                            } else {
                                this.update(k.href, k.rev, k.rel)
                            }
                        }.j16(this, j);
                        j.je1(this.options.selectorsChange, j.z34);
                        if (this.options.selectorsChange == "mouseover") {
                            j.je1("mouseout", j.z34)
                        }
                    }
                    j.j6({outline: "0", display: "inline-block"});
                    if (this.options.preloadSelectorsSmall) {
                        h = new Image();
                        h.src = j.rev
                    }
                    if (this.options.preloadSelectorsBig) {
                        e = new Image();
                        e.src = j.href
                    }
                    this.selectors.push(j)
                }
            }, this)
        }, stop: function (f) {
            try {
                this.pause();
                if (c.j21.touchScreen) {
                    this.c.je2("touchmove", this.z43Bind);
                    this.c.je2("touchend", this.z43Bind)
                } else {
                    this.c.je2("mousemove", this.z43Bind);
                    this.c.je2("mouseout", this.z43Bind)
                }
                if (undefined === f) {
                    this.z4.self.hide()
                }
                if (this.z2) {
                    this.z2.stop()
                }
                this.z6 = null;
                this.z30 = false;
                if (this.selectors !== undefined) {
                    this.selectors.j14(function (e) {
                        if (this.options.selectorsClass != "") {
                            e.j3(this.options.selectorsClass)
                        }
                        if (undefined === f) {
                            e.je2(this.options.selectorsChange, e.z34);
                            if (this.options.selectorsChange == "mouseover") {
                                e.je2("mouseout", e.z34)
                            }
                            e.z34 = null;
                            e.je2("click", e.z36);
                            e.z36 = null
                        }
                    }, this)
                }
                if (this.options.hotspots != "" && $mjs(this.options.hotspots)) {
                    $mjs(this.options.hotspots).hide();
                    $mjs(this.options.hotspots).z31.insertBefore($mjs(this.options.hotspots), $mjs(this.options.hotspots).z32);
                    if (this.c.z33) {
                        this.c.removeChild(this.c.z33)
                    }
                }
                this.z1.unload();
                if (this.options.opacityReverse) {
                    this.c.j3("MagicZoomPup");
                    this.z7.self.j23(1)
                }
                this.z2 = null;
                if (this.z3) {
                    this.c.removeChild(this.z3)
                }
                if (this.hint) {
                    this.hint.hide()
                }
                if (undefined === f) {
                    if (this.hint) {
                        this.c.removeChild(this.hint)
                    }
                    this.hint = null;
                    this.z7.unload();
                    this.c.removeChild(this.z4.self);
                    this.z47.self.parentNode.removeChild(this.z47.self);
                    this.z4 = null;
                    this.z47 = null;
                    this.z1 = null;
                    this.z7 = null;
                    if (!this.options.rightClick) {
                        this.c.je2("contextmenu", c.$Ff)
                    }
                }
                if (this.z24) {
                    clearTimeout(this.z24);
                    this.z24 = null
                }
                this.z44 = null;
                this.c.z33 = null;
                this.z3 = null;
                if (this.c.title == "") {
                    this.c.title = this.c.z46
                }
                this.z28 = -1
            } catch (g) {
            }
        }, start: function (e) {
            if (this.z28 != -1) {
                return
            }
            this.construct(false, e)
        }, update: function (u, k, f, s) {
            var g, w, e, i, p, h, y = null, r = null;
            var j, l, v, q, n, o, z, x, m;
            s = s || null;
            if (c.now() - this.z28 < 300 || this.z28 == -1 || this.ufx) {
                g = 300 - c.now() + this.z28;
                if (this.z28 == -1) {
                    g = 300
                }
                this.z35 = setTimeout(this.update.j24(this, u, k, f, s), g);
                return
            }
            if (s && this.lastSelector == s) {
                return
            } else {
                this.lastSelector = s
            }
            w = function (A) {
                if (undefined != u) {
                    this.c.href = u
                }
                if (undefined === f) {
                    f = ""
                }
                if (this.options.preservePosition) {
                    f = "x: " + this.options.x + "; y: " + this.options.y + "; " + f
                }
                if (undefined != k) {
                    this.z7.update(k);
                    if (A !== undefined) {
                        this.z7.load(A)
                    }
                }
            };
            i = this.z7.width;
            p = this.z7.height;
            this.stop(true);
            if (this.options.selectorsEffect != "false") {
                this.ufx = true;
                h = new a.z48(k);
                if ("pounce" == this.options.selectorsEffect) {
                    m = this.c.href;
                    j = this.selectors.filter(function (A) {
                        return A.href.has(m)
                    });
                    j = (j[0]) ? $mjs(j[0].byTag("img")[0] || j[0]) : this.z7.self;
                    l = this.selectors.filter(function (A) {
                        return A.href.has(u)
                    });
                    l = (l[0]) ? $mjs(l[0].byTag("img")[0] || l[0]) : null;
                    if (null == l) {
                        l = this.z7.self;
                        j = this.z7.self
                    }
                    q = this.z7.self.j8(), n = j.j8(), o = l.j8(), x = j.j7(), z = l.j7()
                } else {
                    this.c.appendChild(h.self);
                    h.self.j6({opacity: 0, position: "absolute", left: "0px", top: "0px"})
                }
                e = function () {
                    var A = {}, C = {}, B = {}, D = null;
                    if ("pounce" == this.options.selectorsEffect) {
                        A.width = [i, x.width];
                        A.height = [p, x.height];
                        A.top = [q.top, n.top];
                        A.left = [q.left, n.left];
                        C.width = [z.width, h.width];
                        C.height = [z.height, h.height];
                        C.top = [o.top, q.top];
                        C.left = [o.left, q.left];
                        B.width = [i, h.width];
                        B.height = [p, h.height];
                        h.self.j32(c.body).j6({
                            position: "absolute",
                            "z-index": 5001,
                            left: C.left[0],
                            top: C.top[0],
                            width: C.width[0],
                            height: C.height[0]
                        });
                        D = $mjs(this.c.firstChild.cloneNode(false)).j32(c.body).j6({
                            position: "absolute",
                            "z-index": 5000,
                            left: A.left[0],
                            top: A.top[0],
                            visibility: "visible"
                        });
                        $mjs(this.c.firstChild).j6({visibility: "hidden"})
                    } else {
                        C = {opacity: [0, 1]};
                        if (i != h.width || p != h.height) {
                            B.width = C.width = A.width = [i, h.width];
                            B.height = C.height = A.height = [p, h.height]
                        }
                        if (this.options.selectorsEffect == "fade") {
                            A.opacity = [1, 0]
                        }
                    }
                    new c.PFX([this.c, h.self, (D || this.c.firstChild)], {
                        duration: this.options.selectorsEffectSpeed,
                        onComplete: function () {
                            if (D) {
                                D.j33();
                                D = null
                            }
                            w.call(this, function () {
                                h.unload();
                                $mjs(this.c.firstChild).j6({visibility: "visible"});
                                $mjs(h.self).j33();
                                h = null;
                                if (A.opacity) {
                                    $mjs(this.c.firstChild).j6({opacity: 1})
                                }
                                this.ufx = false;
                                this.start(f);
                                if (y) {
                                    y.j27(10)
                                }
                            }.j24(this))
                        }.j24(this)
                    }).start([B, C, A])
                };
                h.load(e.j24(this))
            } else {
                w.call(this, function () {
                    this.c.j6({width: this.z7.width + "px", height: this.z7.height + "px"});
                    this.start(f);
                    if (y) {
                        y.j27(10)
                    }
                }.j24(this))
            }
        }, z37: function (f) {
            var e, j, h, g;
            e = null;
            j = [];
            f = f || "";
            if ("" == f) {
                for (g in a.options) {
                    e = a.options[g];
                    switch (c.j1(a.defaults[g.j22()])) {
                        case"boolean":
                            e = e.toString().j18();
                            break;
                        case"number":
                            e = parseFloat(e);
                            break;
                        default:
                            break
                    }
                    j[g.j22()] = e
                }
            } else {
                h = $mjs(f.split(";"));
                h.j14(function (i) {
                    a.z39.j14(function (k) {
                        e = k.exec(i.j26());
                        if (e) {
                            switch (c.j1(a.defaults[e[1].j22()])) {
                                case"boolean":
                                    j[e[1].j22()] = e[4] === "true";
                                    break;
                                case"number":
                                    j[e[1].j22()] = parseFloat(e[4]);
                                    break;
                                default:
                                    j[e[1].j22()] = e[4]
                            }
                        }
                    }, this)
                }, this)
            }
            if (false === j.selectorsEffect) {
                j.selectorsEffect = "false"
            }
            return j
        }, z27: function () {
            var f, e;
            if (!this.z4) {
                this.z4 = {
                    self: $mjs(document.createElement("DIV")).j2("MagicZoomPup").j6({
                        zIndex: 10,
                        position: "absolute",
                        overflow: "hidden"
                    }).hide(), width: 20, height: 20
                };
                this.c.appendChild(this.z4.self)
            }
            if (this.options.entireImage) {
                this.z4.self.j6({"border-width": "0px", cursor: "default"})
            }
            this.z4.z38 = false;
            this.z4.height = this.zoomViewHeight / (this.z1.height / this.z7.height);
            this.z4.width = this.options.zoomWidth / (this.z1.width / this.z7.width);
            if (this.z4.width > this.z7.width) {
                this.z4.width = this.z7.width
            }
            if (this.z4.height > this.z7.height) {
                this.z4.height = this.z7.height
            }
            this.z4.width = Math.round(this.z4.width);
            this.z4.height = Math.round(this.z4.height);
            this.z4.borderWidth = this.z4.self.j19("borderLeftWidth").j17();
            this.z4.self.j6({
                width: (this.z4.width - 2 * (c.j21.backCompat ? 0 : this.z4.borderWidth)) + "px",
                height: (this.z4.height - 2 * (c.j21.backCompat ? 0 : this.z4.borderWidth)) + "px"
            });
            if (!this.options.opacityReverse && !this.options.rightClick) {
                this.z4.self.j23(parseFloat(this.options.opacity / 100));
                if (this.z4.z42) {
                    this.z4.self.removeChild(this.z4.z42);
                    this.z4.z42 = null
                }
            } else {
                if (this.z4.z42) {
                    this.z4.z42.src = this.z7.self.src
                } else {
                    f = this.z7.self.cloneNode(false);
                    f.unselectable = "on";
                    this.z4.z42 = $mjs(this.z4.self.appendChild(f)).j6({position: "absolute", zIndex: 5})
                }
                if (this.options.opacityReverse) {
                    this.z4.self.j23(1)
                } else {
                    if (this.options.rightClick) {
                        this.z4.z42.j23(0.009)
                    }
                    this.z4.self.j23(parseFloat(this.options.opacity / 100))
                }
            }
        }, z43: function (g, f) {
            if (!this.z30 || g === undefined) {
                return false
            }
            var h = (/touch/i).test(g.type) && g.touches.length > 1;
            if ((!this.divTag || g.type != "mouseout") && !h) {
                $mjs(g).stop()
            }
            if (f === undefined) {
                f = $mjs(g).j15()
            }
            if (this.z6 === null || this.z6 === undefined) {
                this.z6 = this.z7.getBox()
            }
            if ("touchend" == g.type || h || f.x > this.z6.right || f.x < this.z6.left || f.y > this.z6.bottom || f.y < this.z6.top) {
                this.pause();
                return false
            }
            this.activatedEx = false;
            if (g.type == "mouseout" || g.type == "touchend") {
                return false
            }
            if (this.options.dragMode && !this.z45) {
                return false
            }
            if (!this.options.moveOnClick) {
                f.x -= this.ddx;
                f.y -= this.ddy
            }
            if ((f.x + this.z4.width / 2) >= this.z6.right) {
                f.x = this.z6.right - this.z4.width / 2
            }
            if ((f.x - this.z4.width / 2) <= this.z6.left) {
                f.x = this.z6.left + this.z4.width / 2
            }
            if ((f.y + this.z4.height / 2) >= this.z6.bottom) {
                f.y = this.z6.bottom - this.z4.height / 2
            }
            if ((f.y - this.z4.height / 2) <= this.z6.top) {
                f.y = this.z6.top + this.z4.height / 2
            }
            this.options.x = f.x - this.z6.left;
            this.options.y = f.y - this.z6.top;
            if (this.z44 === null) {
                if (c.j21.trident) {
                    this.c.style.zIndex = 1
                }
                this.z44 = setTimeout(this.z16, 10)
            }
            if (c.defined(this.hintVisible) && this.hintVisible) {
                this.hintVisible = false;
                this.hint.hide()
            }
            return true
        }, show: function () {
            var n, j, g, f, l, k, i, h, e = this.options, o = this.z4;
            n = o.width / 2;
            j = o.height / 2;
            o.self.style.left = e.x - n + this.z7.border.left + "px";
            o.self.style.top = e.y - j + this.z7.border.top + "px";
            if (this.options.opacityReverse) {
                o.z42.style.left = "-" + (parseFloat(o.self.style.left) + o.borderWidth) + "px";
                o.z42.style.top = "-" + (parseFloat(o.self.style.top) + o.borderWidth) + "px"
            }
            g = (this.options.x - n) * (this.z1.width / this.z7.width);
            f = (this.options.y - j) * (this.z1.height / this.z7.height);
            if (this.z1.width - g < e.zoomWidth) {
                g = this.z1.width - e.zoomWidth;
                if (g < 0) {
                    g = 0
                }
            }
            if (this.z1.height - f < this.zoomViewHeight) {
                f = this.z1.height - this.zoomViewHeight;
                if (f < 0) {
                    f = 0
                }
            }
            if (document.documentElement.dir == "rtl") {
                g = (e.x + o.width / 2 - this.z7.width) * (this.z1.width / this.z7.width)
            }
            g = Math.round(g);
            f = Math.round(f);
            if (e.smoothing === false || (!o.z38 && !e.preservePosition)) {
                this.z1.self.style.left = (-g) + "px";
                this.z1.self.style.top = (-f) + "px"
            } else {
                l = parseInt(this.z1.self.style.left);
                k = parseInt(this.z1.self.style.top);
                i = (-g - l);
                h = (-f - k);
                if (!i && !h) {
                    this.z44 = null;
                    return
                }
                i *= e.smoothingSpeed / 100;
                if (i < 1 && i > 0) {
                    i = 1
                } else {
                    if (i > -1 && i < 0) {
                        i = -1
                    }
                }
                l += i;
                h *= e.smoothingSpeed / 100;
                if (h < 1 && h > 0) {
                    h = 1
                } else {
                    if (h > -1 && h < 0) {
                        h = -1
                    }
                }
                k += h;
                this.z1.self.style.left = l + "px";
                this.z1.self.style.top = k + "px"
            }
            if (!o.z38) {
                if (this.z2) {
                    this.z2.stop();
                    this.z2.options.onComplete = c.$F;
                    this.z2.options.duration = e.zoomFadeInSpeed;
                    this.z47.self.j23(0);
                    this.z2.start({opacity: [0, 1]})
                }
                if (e.zoomPosition != "inner") {
                    o.self.show()
                }
                if (/left|right|top|bottom/i.test(e.zoomPosition) && !this.options.alwaysShowZoom) {
                    var m = this.adjPos();
                    this.z47.self.style.top = m.y + "px";
                    this.z47.self.style.left = m.x + "px"
                } else {
                    this.z47.self.style.top = this.z47.z21
                }
                if (e.opacityReverse) {
                    this.c.j2("MagicZoomPup").j20({"border-width": "0px"});
                    this.z7.self.j23(parseFloat((100 - e.opacity) / 100))
                }
                o.z38 = true
            }
            if (this.z44) {
                this.z44 = setTimeout(this.z16, 1000 / e.fps)
            }
        }, adjPos: function () {
            var f = this.getViewPort(5), e = this.z7.self.j9(), j = this.options.zoomPosition, i = this.z47, g = this.options.zoomDistance, m = i.self.j7(), l = e.top + i.initTopPos, h = e.left + i.initLeftPos, k = {
                x: i.initLeftPos,
                y: i.initTopPos
            };
            if ("left" == j || "right" == j) {
                k.y = Math.max(f.top, Math.min(f.bottom, l + m.height) - m.height) - e.top;
                if ("left" == j && f.left > h) {
                    k.x = (e.left - f.left >= m.width) ? -(e.left - f.left - 2) : (f.right - e.right - 2 > e.left - f.left - 2) ? (e.right - e.left + 2) : -(m.width + 2)
                } else {
                    if ("right" == j && f.right < h + m.width) {
                        k.x = (f.right - e.right >= m.width) ? (f.right - m.width - e.left) : (e.left - f.left - 2 > f.right - e.right - 2) ? -(m.width + 2) : (e.right - e.left + 2)
                    }
                }
            } else {
                if ("top" == j || "bottom" == j) {
                    k.x = Math.max(f.left + 2, Math.min(f.right, h + m.width) - m.width) - e.left;
                    if ("top" == j && f.top > l) {
                        k.y = (e.top - f.top >= m.height) ? -(e.top - f.top - 2) : (f.bottom - e.bottom - 2 > e.top - f.top - 2) ? (e.bottom - e.top + 2) : -(m.height + 2)
                    } else {
                        if ("bottom" == j && f.bottom < l + m.height) {
                            k.y = (f.bottom - e.bottom >= m.height) ? (f.bottom - m.height - e.top) : (e.top - f.top - 2 > f.bottom - e.bottom - 2) ? -(m.height + 2) : (e.bottom - e.top + 2)
                        }
                    }
                }
            }
            return k
        }, getViewPort: function (g) {
            g = g || 0;
            var f = (c.j21.touchScreen) ? {
                width: window.innerWidth,
                height: window.innerHeight
            } : $mjs(window).j7(), e = $mjs(window).j10();
            return {left: e.x + g, right: e.x + f.width - g, top: e.y + g, bottom: e.y + f.height - g}
        }, activate: function (e) {
            e = (c.defined(e)) ? e : true;
            this.activatedEx = true;
            if (!this.z1) {
                this.z18();
                return
            }
            if (this.options.disableZoom) {
                return
            }
            this.z30 = true;
            if (e) {
                if (!this.options.preservePosition) {
                    this.options.x = this.z7.width / 2;
                    this.options.y = this.z7.height / 2
                }
                this.show()
            }
        }, pause: function () {
            if (this.z44) {
                clearTimeout(this.z44);
                this.z44 = null
            }
            if (!this.options.alwaysShowZoom && this.z4.z38) {
                this.z4.z38 = false;
                this.z4.self.hide();
                if (this.z2) {
                    this.z2.stop();
                    this.z2.options.onComplete = this.z47.z22;
                    this.z2.options.duration = this.options.zoomFadeOutSpeed;
                    var e = this.z47.self.j19("opacity");
                    this.z2.start({opacity: [e, 0]})
                } else {
                    this.z47.hide()
                }
                if (this.options.opacityReverse) {
                    this.c.j3("MagicZoomPup");
                    this.z7.self.j23(1)
                }
            }
            this.z6 = null;
            if (this.options.clickToActivate) {
                this.z30 = false
            }
            if (this.options.dragMode) {
                this.z45 = false
            }
            if (this.hint) {
                this.hintVisible = true;
                this.hint.show()
            }
            if (c.j21.trident) {
                this.c.style.zIndex = 0
            }
        }, mousedown: function (h) {
            var f = h.getButton();
            if (3 == f) {
                return true
            }
            if (!((/touch/i).test(h.type) && h.touches.length > 1)) {
                $mjs(h).stop()
            }
            if ("click" == this.options.initializeOn && !this.z7) {
                this.initMouseEvent = h;
                this.z18();
                return
            }
            if ("mouseover" == this.options.initializeOn && !this.z7 && h.type == "mouseover") {
                this.initMouseEvent = h;
                this.z18();
                this.c.je2("mouseover", this.z14);
                return
            }
            if (this.options.disableZoom) {
                return
            }
            if (this.z7 && !this.z1.ready) {
                return
            }
            if (this.z1 && this.options.clickToDeactivate && this.z30) {
                this.z30 = false;
                this.pause();
                return
            }
            if (this.z1 && !this.z30) {
                this.z30 = true;
                this.z43(h)
            }
            if (this.z30 && this.options.dragMode) {
                this.z45 = true;
                if (!this.options.moveOnClick) {
                    if (c.j21.touchScreen && (this.z6 === null || this.z6 === undefined)) {
                        this.z6 = this.z7.getBox()
                    }
                    var g = h.j15();
                    this.ddx = g.x - this.options.x - this.z6.left;
                    this.ddy = g.y - this.options.y - this.z6.top;
                    if (Math.abs(this.ddx) > this.z4.width / 2 || Math.abs(this.ddy) > this.z4.height / 2) {
                        this.z45 = false;
                        return
                    }
                } else {
                    this.z43(h)
                }
            }
        }, mouseup: function (g) {
            var f = g.getButton();
            if (3 == f) {
                return true
            }
            $mjs(g).stop();
            if (this.options.dragMode) {
                this.z45 = false
            }
        }
    };
    if (c.j21.trident) {
        try {
            document.execCommand("BackgroundImageCache", false, true)
        } catch (b) {
        }
    }
    if (c.j21.touchScreen) {
        $mjs(document).je1("touchstart", function (f) {
        })
    }
    $mjs(document).je1("domready", function () {
        if (!c.j21.touchScreen) {
            $mjs(document).je1("mousemove", a.z8)
        }
        a.refresh()
    });
    return a
})(magicJS);