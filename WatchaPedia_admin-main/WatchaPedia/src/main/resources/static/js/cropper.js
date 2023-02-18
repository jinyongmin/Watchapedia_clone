/**
 * Minified by jsDelivr using Terser v5.14.1.
 * Original file: /npm/@lemonadejs/cropper@1.6.2/dist/cropper.js
 *
 * Do NOT use SRI with dynamically generated files! More information: https://www.jsdelivr.com/using-sri-with-dynamic-files
 */
!function (e, t) {
    "object" == typeof exports && "undefined" != typeof module
        ? module.exports = t()
        : "function" == typeof define && define.amd
            ? define(t)
            : e.Cropper = t()
}(this, (function () {
    "use strict";
    if (void 0 === e)
        if ("function" == typeof require)
            var e = require("lemonadejs");

        else if (window.lemonade)
            e = window.lemonade;

    if (void 0 === t)
        if ("function" == typeof require)
            var t = require("jsuites");

        else if (window.jSuites)
            t = window.jSuites;

    if ("undefined" == typeof cropper) {
        if ("function" == typeof require)
            var n = require("@jsuites/cropper");
        else if (window.cropper)
            n = window.cropper

    } else
        n = cropper;

    return function () {
        var i = this,
            o = i.original
                ? 1
                : 0,
            a = i.width || 240,
            l = i.height || 240,
            s = null,
            r = null,
            d = null;
        i.cropperArea = null,
            i.brightness = 0,
            i.contrast = 0,
            i.greyscale = 0,
            i.createModal = function (e) {
                s = t.modal(e, {
                    closed: !0,
                    width: "800px",
                    height: "680px",
                    title: "Photo Upload",
                    padding: "0",
                    icon: "photo"
                })
            },
            i.createCropper = function (e) {
                var o = t.getWindowWidth();
                if (o < 800)
                    var s = [
                            o, o
                        ],
                        d = [o, o];
                else
                    s = [
                        798, 360
                    ],
                        d = [a, l];
                r = n(e, {
                    area: s,
                    crop: d,
                    allowResize: !1,
                    onchange: function (e, t) {
                        t && i.setControls(!0)
                    }
                })
            },
            i.createMenu = function (e) {
                d = t.contextmenu(e, {
                    onclick: function (e, t) {
                        e.close(),
                            t.stopPropagation()
                    }
                })
            },
            i.createControls = function (e) {
                t
                    .tabs(e.children[0], {
                        data: [
                            {
                                title: "Crop",
                                icon: "crop",
                                width: "100px"
                            }, {
                                title: "Adjusts",
                                icon: "image",
                                width: "100px"
                            }
                        ],
                        padding: "10px",
                        animation: !0,
                        position: "bottom"
                    })
                    .content
                    .style
                    .backgroundColor = "#eee"
            },
            i.updateZoom = function (e) {
                r.zoom(e.value)
            },
            i.updateRotate = function (e) {
                r.rotate(e.value)
            },
            i.setBrightness = function (e) {
                r.brightness(e.value)
            },
            i.setContrast = function (e) {
                r.contrast(e.value)
            },
            i.setGreyscale = function (e) {
                r.greyScale(e.value)
            },
            i.updatePhoto = function () {
                if (i
                    .cropperArea
                    .classList
                    .contains("jcrop_edition")) {
                    i.image.innerHTML = "";
                    var e = r.getCroppedImage();
                    r.getCroppedAsBlob((function (t) {
                        var n = window.URL.createObjectURL(t),
                            a = {
                                file: n,
                                content: e.src,
                                extension: e.content.extension
                            };
                        1 == o && (a.original = r.getImage().src),
                            e.src = n,
                        i.name && e.classList.remove("jfile"),
                            i.value = [a],
                            i.image.appendChild(e)
                    })),
                        setTimeout((function () {
                            s.close()
                        }))
                }
            },
            i.uploadPhoto = function () {
                t.click(r.getFileInput())
            },
            i.deletePhoto = function () {
                i.image && (r.reset(), i.setControls(!1), i.image.innerHTML = "", i.value = null)
            },
            i.setControls = function (e) {
                var t = i.el.querySelectorAll("input.controls");
                if (0 == e)
                    for (var n = 0; n < t.length; n++)
                        t[n].setAttribute("disabled", "disabled");

                else
                    for (n = 0; n < t.length; n++)
                        t[n].removeAttribute("disabled");


                for (n = 0; n < t.length; n++)
                    "range" === t[n].type && (t[n].value = 0)

            },
            i.getValue = function () {
                return i.value
            },
            i.setValue = function (e) {
                if (e) {
                    if ("string" == typeof e && (e =
                        { file: e
                        }), e.file) {
                        var t = document.createElement("img");
                        t.setAttribute("src", e.file),
                            t.setAttribute("tabindex", -1),
                            i.image.innerHTML = "",
                            i.image.appendChild(t)
                    }
                    e.original && r.addFromFile(e.original),
                        i.value = [e]
                } else
                    i.deletePhoto()

            },
            i.open = function () {
                s.isOpen() || s.open()
            },
            i.onload = function () {
                i
                    .image
                    .style
                    .maxWidth = i.width + "px",
                    i
                        .image
                        .style
                        .maxHeight = i.height + "px"
            };
        var c = e.element("\n            <div name=\" {{self.name}}\" value=\" {{self.value}}\">\n                <div @ref='self.image' class=\"jphoto jcropper\"></div>\n                <div @ready='self.createModal(this)'>\n                    <div @ready='self.createCropper(this)' @ref='self.cropperArea'></div>\n                    <div @ready='self.createControls(this)' class=\"controls\">\n                        <div role='tabs'>\n                            <div role='headers'>\n                                <div style=\"background-color: white; padding: 15px !important;\"></div>\n                                <div style=\"background-color: white;\"></div>\n                            </div>\n                            <div role='content' style='background-color: #ccc;'>\n                                <div>\n                                    <div class=\"center row\">\n                                        <label class=\"f1 p6\" style=\"padding-top:0px\"> Zoom <input type='range' step='.05' min='0.1' max='5.45' value='1' oninput='self.updateZoom(this)' style=\"margin-top:10px;\" class='jrange controls' disabled='disabled'></label>\n                                        <label class=\"f1 p6\" style=\"padding-top:0px\"> Rotate <input type='range' step='.05' min='-1' max='1' value='0' oninput='self.updateRotate(this)' style=\"margin-top:10px;\" class='jrange controls' disabled='disabled'></label>\n                                    </div>\n                                </div>\n                                <div>\n                                    <div class=\"center row\">\n                                        <label class=\"f1 p6\" style=\"padding-top:0px\"> Brigthness <input type='range' min='-1' max='1' step='.05' value='0' @bind='self.brightness' oninput='self.setBrightness(this)' style=\"margin-top:10px;\" class='jrange controls' disabled='disabled'></label>\n                                        <label class=\"f1 p6\" style=\"padding-top:0px\"> Contrast <input type='range' min='-1' max='1' step='.05' value='0' @bind='self.contrast' oninput='self.setContrast(this)' style=\"margin-top:10px;\" class='jrange controls' disabled='disabled'></label>\n                                    </div>\n                                </div>\n                            </div>\n                        </div>\n                        <div class='row p20' style='border-top: 1px solid #aaa'>\n                            <div class='column p6 f1'>\n                                <input type='button' value='Save Photo' class='jbutton dark controls w100' style='min-width: 140px;' onclick='self.updatePhoto()' disabled='disabled'>\n                            </div><div class='column p6'>\n                                <input type='button' value='Upload Photo' class='jbutton dark w100' style='min-width: 140px;' onclick='self.uploadPhoto()'>\n                            </div><div class='column p6' style='text-align:right'>\n                                <input type='button' value='Delete Photo' class='jbutton dark controls w100' style='min-width: 140px;' onclick='self.deletePhoto()' disabled='disabled'>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n                <div @ready=\"self.createMenu(this)\"></div>\n            </div>", i);
        return c.addEventListener("mousedown", (function (e) {
            if ((e = e || window.event).buttons)
                var t = e.buttons;
            else if (e.button)
                t = e.button;
            else
                t = e.which;

            1 == t
                ? i.open()
                : "IMG" == e.target.tagName && e.target.focus()
        })),
            c.addEventListener("contextmenu", (function (e) {
                "IMG" == e.target.tagName && (d.open(e, [
                    {
                        title: t.translate("Change image"),
                        icon: "edit",
                        onclick: function () {
                            i.open()
                        }
                    }, {
                        title: t.translate("Delete image"),
                        icon: "delete",
                        shortcut: "DELETE",
                        onclick: function () {
                            e.target.remove()
                        }
                    }
                ]), e.preventDefault())
            })),
            c.addEventListener("onkeydown", (function (e) {
                "Delete" == e.key && "IMG" == e.target.tagName && i.deletePhoto()
            })),
            c.val = function (e) {
                if (void 0 === e)
                    return i.getValue();

                i.setValue(e)
            },
            c
    }
}));
// # sourceMappingURL=/sm/d927a8401b37f20f5959ad4f1b175e6b6f9866e8d5306f0abcc973c70df8a479.map