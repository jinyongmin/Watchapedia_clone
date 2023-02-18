/**
 * Minified by jsDelivr using Terser v5.15.1.
 * Original file: /npm/@jsuites/cropper@1.6.2/cropper.js
 *
 * Do NOT use SRI with dynamically generated files! More information: https://www.jsdelivr.com/using-sri-with-dynamic-files
 */
!function (e, t) {
    "object" == typeof exports && "undefined" != typeof module
        ? module.exports = t()
        : "function" == typeof define && define.amd
            ? define(t)
            : e.cropper = t()
}(this, (function () {
    "use strict";
    if (void 0 === e)
        if ("function" == typeof require)
            var e = require("jsuites");

        else if (window.jSuites)
            e = window.jSuites;

    var t = function (t, o) {
        if (t.crop)
            return t.crop.setOptions(o, !0);

        var i = {
            options: {}
        };
        t.classList.add("jcrop"),
            t.classList.add("jupload");
        var n = document.createElement("div");
        n.classList.add("jcrop-area"),
            t.appendChild(n);
        var a = document.createElement("canvas"),
            s = a.getContext("2d", {
                willReadFrequently: !0
            });
        t.appendChild(a);
        i.image = new Image,
            i.image.onload = function () {
                i.resetCanvas();
                var o = i.options.area[0] / this.naturalWidth,
                    n = i.options.area[1] / this.naturalHeight,
                    a = Math.min(n, o);
                this.width = this.naturalWidth * a,
                    this.height = this.naturalHeight * a,
                    function () {
                        if (t.clientHeight > i.image.height)
                            var e = (t.clientHeight - i.image.height) / 2;
                        else
                            e = 0;

                        if (t.clientWidth > i.image.width)
                            var o = (t.clientWidth - i.image.width) / 2;
                        else
                            o = 0;
                        i.image.left = o,
                            i.image.top = e,
                            s.translate(o, e),
                            s.drawImage(i.image, 0, 0, i.image.width, i.image.height)
                    }(),
                    t.classList.add("jcrop_edition"),
                e.getWindowWidth() > 800 && i.resetCropSelection(),
                    d(),
                "function" == typeof i.options.onchange && i.options.onchange(t, i.image)
            };
        var r = {
                zoom: {
                    origin: {
                        x: null,
                        y: null
                    },
                    scale: 1,
                    fingerDistance: 0
                },
                contrast: 0,
                brightness: 0,
                rotate: 0,
                saturation: 0
            },
            l = document.createElement("canvas"),
            c = l.getContext("2d", {
                willReadFrequently: !0
            }),
            g = document.createElement("img"),
            d = function () {
                l.width = i.image.width,
                    l.height = i.image.height,
                    c.clearRect(0, 0, c.width, c.height),
                    g.width = l.width,
                    g.height = l.height,
                i.image && c.drawImage(i.image, 0, 0, i.image.width, i.image.height),
                r.contrast && p(),
                r.brightness && w(),
                r.greyScale && runGreyScale(),
                r.saturation && runSaturation(),
                    g.src = l.toDataURL(i.getImageType),
                    g.onload = function () {
                        s.drawImage(g, 0, 0, g.width, g.height)
                    }
            };
        i.setOptions = function (o, n) {
            var s = {
                area: [
                    800, 600
                ],
                crop: [
                    200, 150
                ],
                value: null,
                onload: null,
                onchange: null,
                remoteParser: null,
                allowResize: !0,
                text: {
                    extensionNotAllowed: "The extension is not allowed"
                },
                eventListeners: {}
            };
            for (var r in s)
                o && o.hasOwnProperty(r)
                    ? i.options[r] = o[r]
                    : void 0 !== i.options[r] && !0 !== n || (i.options[r] = s[r]);

            return e.getWindowWidth() < 800 && (i.options.area[0] || (i.options.area[0] = 2 * window.clientWidth), i.options.area[1] || (i.options.area[1] = 2 * window.clientHeight)),
                t.style.width = i.options.area[0] + "px",
                t.style.height = i.options.area[1] + "px",
                a.width = i.options.area[0],
                a.height = i.options.area[1],
                i.reset(),
            "string" == typeof i.options.value && (i.image.src = i.options.value),
                i
        },
            i.resetCropSelection = function () {
                n.style.left = i.options.area[0] / 2 - i.options.crop[0] / 2 + "px",
                    n.style.top = i.options.area[1] / 2 - i.options.crop[1] / 2 + "px",
                    n.style.width = i.options.crop[0] + "px",
                    n.style.height = i.options.crop[1] + "px",
                    n.style.zIndex = 1
            },
            i.getCropSelection = function () {
                i.getSelectionCoordinates()
            },
            i.resetCanvas = function () {
                r = {
                    zoom: {
                        origin: {
                            x: null,
                            y: null
                        },
                        scale: 1
                    },
                    contrast: 0,
                    brightness: 0,
                    rotate: 0,
                    greyScale: 0,
                    saturation: 0
                },
                    s.setTransform(1, 0, 0, 1, 0, 0),
                    s.clearRect(0, 0, a.width, a.height)
            },
            i.reset = function () {
                i.resetCanvas(),
                    i.resetCropSelection(),
                    r = {
                        zoom: {
                            origin: {
                                x: null,
                                y: null
                            },
                            scale: 1
                        },
                        contrast: 0,
                        brightness: 0,
                        rotate: 0,
                        greyScale: 0,
                        saturation: 0
                    },
                    b.value = "",
                    t.classList.remove("jcrop_edition")
            };
        var h = function (e) {
                if ("function" == typeof i.options.eventListeners[e]) {
                    var t = {
                        zoom: r.zoom.scale,
                        rotate: r.rotate,
                        brightness: r.brightness,
                        contrast: r.contrast
                    };
                    i.options.eventListeners[e](t[e])
                }
            },
            p = function () {
                for (var e = r.contrast, t = c.getImageData(0, 0, l.width, l.height), o = t.data, i =(( e *= 255) + 255) / (255.01 - e), n = 0; n < o.length; n += 4)
                    o[n] = i * (o[n] - 128) + 128,
                        o[n + 1] = i * (o[n + 1] - 128) + 128,
                        o[n + 2] = i * (o[n + 2] - 128) + 128;

                return c.putImageData(t, 0, 0),
                    t
            };
        i.contrast = function (e) {
            Number.isNaN(parseFloat(e)) || (r.contrast = e),
                h("contrast"),
                d()
        };
        var f,
            u,
            m,
            v,
            y,
            w = function () {
                var e = r.brightness,
                    t = c.getImageData(0, 0, l.width, l.height),
                    o = t.data;
                e *= 255;
                for (var i = 0; i < o.length; i += 4)
                    o[i] += e,
                        o[i + 1] += e,
                        o[i + 2] += e;

                return c.putImageData(t, 0, 0),
                    t
            };
        i.brightness = function (e) {
            Number.isNaN(parseFloat(e)) || (r.brightness = e),
                h("brightness"),
                d()
        },
            i.getImageType = function () {
                var e = i
                    .image
                    .src
                    .substr(0, 20);
                return e.includes("data")
                    ? e.split("/")[1].split(";")[0]
                    : null
            },
            i.getSelectionCoordinates = function () {
                return {
                    left: n.offsetLeft,
                    top: n.offsetTop,
                    right: n.offsetLeft + n.clientWidth,
                    bottom: n.offsetTop + n.clientHeight
                }
            },
            i.getCroppedImage = function () {
                return e.image.create({extension: i.getImageType(), file: i.getCroppedContent(), name: "", size: ""})
            },
            i.getCroppedContent = function () {
                var e = i.getSelectionCoordinates(),
                    t = document.createElement("canvas"),
                    o = t.getContext("2d", {
                        willReadFrequently: !0
                    });
                t.width = n.clientWidth,
                    t.height = n.clientHeight;
                var a = s.getImageData(e.left, e.top, n.clientWidth, n.clientHeight);
                return o.putImageData(a, 0, 0),
                    t.toDataURL(i.getImageType())
            },
            i.getCroppedAsBlob = function (e) {
                var t = i.getSelectionCoordinates(),
                    o = document.createElement("canvas"),
                    a = o.getContext("2d", {
                        willReadFrequently: !0
                    });
                o.width = n.clientWidth,
                    o.height = n.clientHeight;
                var r = s.getImageData(t.left, t.top, n.clientWidth, n.clientHeight);
                a.putImageData(r, 0, 0),
                    o.toBlob(e)
            },
            i.getImage = function () {
                return i.image
            },
            i.getFileInput = function () {
                return b
            },
            i.getCanvas = function () {
                return a
            },
            i.addFromFile = function (e) {
                if ("image" == e.type.split("/")[0]) {
                    var t = new FileReader;
                    t.addEventListener("load", (function (e) {
                        i.image.src = e.target.result
                    })),
                        t.readAsDataURL(e)
                } else
                    alert(i
                        .options
                        .text
                        .extensionNotAllowed)

            },
            i.addFromUrl = function (e) {
                "data" == e.substr(0, 4) || i.options.remoteParser
                    ? (e = i.options.remoteParser + e, i.image.src = e)
                    : console.error("remoteParser not defined in your initialization")
            };
        var x = function () {
                s.setTransform(1, 0, 0, 1, 0, 0),
                    s.clearRect(0, 0, a.width, a.height),
                    function () {
                        if (m && m !== r
                            .zoom
                            .origin
                            .x) {
                            var e = Math.abs(r
                                .zoom
                                .origin
                                .x - f - i.image.left);
                            e /= y,
                                e -= r
                                    .zoom
                                    .origin
                                    .x - i.image.left,
                                i.image.left -= e
                        }
                        v && v !== r
                            .zoom
                            .origin
                            .y && (e = Math.abs(r
                            .zoom
                            .origin
                            .y - u - i.image.top), e /= y, e -= r
                            .zoom
                            .origin
                            .y - i.image.top, i.image.top -= e);
                        f = r
                            .zoom
                            .origin
                            .x - i.image.left -(r
                            .zoom
                            .origin
                            .x - i.image.left) * r.zoom.scale,
                            u = r
                                .zoom
                                .origin
                                .y - i.image.top -(r
                                .zoom
                                .origin
                                .y - i.image.top) * r.zoom.scale,
                            m = r
                                .zoom
                                .origin
                                .x,
                            v = r
                                .zoom
                                .origin
                                .y,
                            y = r.zoom.scale,
                            s.translate(i.image.left + f, i.image.top + u)
                    }(),
                1 !== r.zoom.scale && z(),
                r.rotate && L(),
                    r.brightness || r.contrast
                        ? s.drawImage(g, 0, 0, g.width, g.height)
                        : s.drawImage(i.image, 0, 0, i.image.width, i.image.height)
            },
            z = function () {
                s.scale(r.zoom.scale, r.zoom.scale)
            };
        i.zoom = function (e) {
            e && (r.zoom.scale = e),
                h("zoom"),
                x()
        };
        var L = function () {
            var e = r.rotate;
            e *= 180,
                s.translate(i.image.width / 2, i.image.height / 2),
                s.rotate(e * Math.PI / 180),
                s.translate(- i.image.width / 2, - i.image.height / 2)
        };
        i.rotate = function (e) {
            Number.isNaN(parseFloat(e)) || (r.rotate = e),
                h("rotate"),
                x()
        };
        var b = document.createElement("input");
        b.type = "file",
            b.setAttribute("accept", "image/*"),
            b.onchange = function () {
                for (var e = 0; e < this.files.length; e++)
                    i.addFromFile(this.files[e])

            };
        var C = null,
            E = null,
            I = {
                mousedown: !1,
                mouseX: 0,
                mouseY: 0
            },
            X = function (e) {
                e
                    .target
                    .classList
                    .contains("jcrop-area") || (I.mousedown =! 0),
                    e.changedTouches && e.changedTouches[0]
                        ? (I.mouseX = e.changedTouches[0].clientX, I.mouseY = e.changedTouches[0].clientY)
                        : (I.mouseX = e.clientX, I.mouseY = e.clientY),
                e.touches && 2 == e.touches.length && (I.mousedown =! 1, E =! 0, Y(e))
            },
            D = function (e) {
                if (t.classList.contains("jcrop_edition") && ! E) {
                    if (e.changedTouches && e.changedTouches[0])
                        var o = e.changedTouches[0].clientX,
                            n = e.changedTouches[0].clientY;
                    else
                        o = e.clientX,
                            n = e.clientY;

                    var a = o,
                        s = a - I.mouseX;
                    I.mouseX = a;
                    var l = n,
                        c = l - I.mouseY;
                    I.mouseY = l,
                    I.mousedown && (i.image.left += s / r.zoom.scale, i.image.top += c / r.zoom.scale, x()),
                        e.preventDefault()
                }
                E && T(e)
            };
        document.addEventListener("mouseup", (function (e) {
            I.mousedown = !1
        })),
            t.addEventListener("mouseup", (function (e) {
                C = !1
            })),
            t.addEventListener("mousedown", (function (e) {
                if (e
                    .target
                    .classList
                    .contains("jcrop-area")) {
                    var t = e.target.getBoundingClientRect(),
                        o = "" !== e
                            .target
                            .style
                            .left
                            ? e
                                .target
                                .style
                                .left
                            : "0px",
                        i = "" !== e
                            .target
                            .style
                            .top
                            ? e
                                .target
                                .style
                                .top
                            : "0px";
                    if (e
                        .target
                        .style
                        .cursor) {
                        C = {
                            e: e.target,
                            x: e.clientX,
                            y: e.clientY,
                            w: t.width,
                            h: t.height,
                            d: e
                                .target
                                .style
                                .cursor,
                            xOffset: e.clientX - parseInt(o.slice(0, o.length - 2)),
                            yOffset: e.clientY - parseInt(i.slice(0, i.length - 2))
                        },
                        e
                            .target
                            .style
                            .width || (e
                            .target
                            .style
                            .width = t.width + "px"),
                        e
                            .target
                            .style
                            .height || (e
                            .target
                            .style
                            .height = t.height + "px");
                        var n = window.getSelection();
                        if (n.rangeCount)
                            for (var a = 0; a < n.rangeCount; a++)
                                n.removeRange(n.getRangeAt(a))


                    }
                } else
                    C = !0

            })),
            t.addEventListener("mousemove", (function (e) {
                    if (void 0 !== typeof(e = e || window.event).buttons)
                        var o = e.buttons;
                    else if (void 0 !== typeof e.button)
                        o = e.button;
                    else
                        o = e.which;

                    if (! e.buttons && e
                        .target
                        .classList
                        .contains("jcrop-area")) {
                        var a = e.target.getBoundingClientRect();
                        1 == i.options.allowResize
                            ? e.clientY - a.top < 5
                                ? a.width -(e.clientX - a.left) < 5
                                    ? e
                                        .target
                                        .style
                                        .cursor = "ne-resize"
                                    : e.clientX - a.left < 5
                                        ? e
                                            .target
                                            .style
                                            .cursor = "nw-resize"
                                        : e
                                            .target
                                            .style
                                            .cursor = "n-resize"
                                : a.height -(e.clientY - a.top) < 5
                                    ? a.width -(e.clientX - a.left) < 5
                                        ? e
                                            .target
                                            .style
                                            .cursor = "se-resize"
                                        : e.clientX - a.left < 5
                                            ? e
                                                .target
                                                .style
                                                .cursor = "sw-resize"
                                            : e
                                                .target
                                                .style
                                                .cursor = "s-resize"
                                    : a.width -(e.clientX - a.left) < 5
                                        ? e
                                            .target
                                            .style
                                            .cursor = "e-resize"
                                        : e.clientX - a.left < 5
                                            ? e
                                                .target
                                                .style
                                                .cursor = "w-resize"
                                            : e
                                                .target
                                                .style
                                                .cursor = "move"
                            : e
                                .target
                                .style
                                .cursor = "move"
                    }
                    if (1 == o && C && C.d)
                        if ("move" == C.d) {
                            var s = e.clientX - C.xOffset,
                                r = e.clientY - C.yOffset;
                            s < 0
                                ? C
                                    .e
                                    .style
                                    .left = "0px"
                                : s > t.offsetWidth - n.offsetWidth - 2
                                    ? C
                                        .e
                                        .style
                                        .left = t.offsetWidth - n.offsetWidth - 2 + "px"
                                    : C
                                        .e
                                        .style
                                        .left = s + "px",
                                r < 0
                                    ? C
                                        .e
                                        .style
                                        .top = "0px"
                                    : r > t.offsetHeight - n.offsetHeight - 2
                                        ? C
                                            .e
                                            .style
                                            .top = t.offsetHeight - n.offsetHeight - 2 + "px"
                                        : C
                                            .e
                                            .style
                                            .top = r + "px"
                        }
                        else {
                            if ("e-resize" == C.d || "ne-resize" == C.d || "se-resize" == C.d) {
                                var l = C.w + e.clientX - C.x,
                                    c = C
                                        .e
                                        .style
                                        .left
                                        .slice(0, C
                                            .e
                                            .style
                                            .left
                                            .length - 2);
                                l < i.options.crop[0]
                                    ? C
                                        .e
                                        .style
                                        .width = i.options.crop[0] + "px"
                                    : l + parseInt(c) > t.offsetWidth - 2
                                        ? C
                                            .e
                                            .style
                                            .width = t.offsetWidth - c - 2 + "px"
                                        : C
                                            .e
                                            .style
                                            .width = C.w + e.clientX - C.x + "px"
                            }
                            else if ("w-resize" == C.d || "nw-resize" == C.d || "sw-resize" == C.d) {
                                var g = e.clientX - C.xOffset;
                                (l = C.x + C.w - e.clientX) < i.options.crop[0]
                                    ? (C
                                        .e
                                        .style
                                        .left = C.x + C.w - i.options.crop[0] - C.xOffset + "px", C
                                        .e
                                        .style
                                        .width = i.options.crop[0] + "px")
                                    : g < 0
                                        ? (C
                                            .e
                                            .style
                                            .left = "0px", C
                                            .e
                                            .style
                                            .width = C.x + C.w - C.xOffset + "px")
                                        : (C
                                            .e
                                            .style
                                            .left = g + "px", C
                                            .e
                                            .style
                                            .width = l + "px")
                            }
                            if ("s-resize" == C.d || "se-resize" == C.d || "sw-resize" == C.d) {
                                var d = C.h + e.clientY - C.y;
                                c = C
                                    .e
                                    .style
                                    .top
                                    .slice(0, C
                                        .e
                                        .style
                                        .top
                                        .length - 2);
                                d < i.options.crop[1]
                                    ? C
                                        .e
                                        .style
                                        .height = i.options.crop[1] + "px"
                                    : d + parseInt(c) > t.offsetHeight - 2
                                        ? C
                                            .e
                                            .style
                                            .height = t.offsetHeight - parseInt(c) - 2 + "px"
                                        : C
                                            .e
                                            .style
                                            .height = d + "px"
                            } else if ("n-resize" == C.d || "ne-resize" == C.d || "nw-resize" == C.d) {
                                g = e.clientY - C.yOffset;
                                (d = C.h + C.y - e.clientY) < i.options.crop[1]
                                    ? (C
                                        .e
                                        .style
                                        .top = C.y + C.h - i.options.crop[1] - C.yOffset + "px", C
                                        .e
                                        .style
                                        .height = i.options.crop[1] + "px")
                                    : g < 0
                                        ? (C
                                            .e
                                            .style
                                            .top = "0px", C
                                            .e
                                            .style
                                            .height = C.y + C.h - C.yOffset + "px")
                                        : (C
                                            .e
                                            .style
                                            .top = g + "px", C
                                            .e
                                            .style
                                            .height = d + "px")
                            }
                        }
                })
            ),
            t.addEventListener("touchstart", X),
            t.addEventListener("touchend", (function (e) {
                E && (E =! 1)
            })),
            t.addEventListener("touchmove", D),
            t.addEventListener("mousedown", X),
            t.addEventListener("mousemove", D),
            t.addEventListener("dblclick", (function (t) {
                e.click(b)
            })),
            t.addEventListener("dragenter", (function (e) {
                t.style.border = "1px dashed #000"
            })),
            t.addEventListener("dragleave", (function (e) {
                t.style.border = "1px solid #eee"
            })),
            t.addEventListener("dragstop", (function (e) {
                t.style.border = "1px solid #eee"
            })),
            t.addEventListener("dragover", (function (e) {
                e.preventDefault()
            })),
            t.addEventListener("drop", (function (e) {
                e.preventDefault(),
                    e.stopPropagation();
                var o = (e.originalEvent || e).dataTransfer.getData("text/html");
                if ((e.originalEvent || e)
                    .dataTransfer
                    .files
                    .length)
                    for (var n = 0; n < e
                        .dataTransfer
                        .files
                        .length; n++)
                        i.addFromFile(e.dataTransfer.files[n]);

                else if (o) {
                    var a = document.createElement("div");
                    a.innerHTML = o;
                    var s = a.querySelector("img");
                    s && i.addFromUrl(s.src)
                }
                return t.style.border = "1px solid #eee",
                    !1
            })),
            t.addEventListener("wheel", (function (e) {
                if (t.classList.contains("jcrop_edition")) {
                    e.deltaY > 0
                        ? r.zoom.scale > .1 && (r.zoom.scale *= .9)
                        : r.zoom.scale < 5 && (r.zoom.scale *= 1.1),
                        r.zoom.scale = parseFloat(r
                            .zoom
                            .scale
                            .toFixed(2));
                    var o = t.getBoundingClientRect(),
                        n = e.clientX - o.left,
                        a = e.clientY - o.top;
                    0 != s.getImageData(n, a, 1, 1).data[3] && (r
                        .zoom
                        .origin
                        .x = n, r
                        .zoom
                        .origin
                        .y = a),
                        i.zoom(),
                        e.preventDefault()
                }
            })),
            t.addEventListener("click", (function (o) {
                t.classList.contains("jcrop_edition") || e.click(b)
            })),
            i.setOptions(o),
        "function" == typeof i.options.onload && i.options.onload(t, i);
        var Y = function (e) {
                var o = t.getBoundingClientRect();
                r.zoom.fingerDistance = Math.hypot(e.touches[0].pageX - e.touches[1].pageX, e.touches[0].pageY - e.touches[1].pageY),
                    r
                        .zoom
                        .origin
                        .x = (e.touches[0].pageX - o.left + (e.touches[1].pageX - o.left)) / 2,
                    r
                        .zoom
                        .origin
                        .y = (e.touches[0].pageY - o.top + (e.touches[1].pageY - o.top)) / 2
            },
            T = function (e) {
                e.preventDefault();
                var t = Math.hypot(e.touches[0].pageX - e.touches[1].pageX, e.touches[0].pageY - e.touches[1].pageY);
                if (t > r.zoom.fingerDistance) {
                    var o = t - r.zoom.fingerDistance;
                    (n = r.zoom.scale + r.zoom.scale * o * .0025) <= 5.09 && i.zoom(n)
                }
                if (t < r.zoom.fingerDistance) {
                    var n;
                    o = r.zoom.fingerDistance - t;
                    (n = r.zoom.scale - r.zoom.scale * o * .0025) >= .1 && i.zoom(n)
                }
                r.zoom.fingerDistance = t
            };
        return t.crop = i,
            i
    };
    return window.jSuites && e.setExtensions({crop: t}), t
}));// # sourceMappingURL=/sm/ecfc3665f5fb27d122e49967df28429f31cc35ab79382c63d64c5a9d1e7dac93.map