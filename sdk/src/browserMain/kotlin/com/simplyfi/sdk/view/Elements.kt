package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLBaseElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLScriptElement

@Composable
fun Root(
    tagName: String = "th-root",
) = TagElement<HTMLElement>(tagName, null, null)

@Composable
fun Script(
    attrs: AttrsScope<HTMLScriptElement>.() -> Unit = {}
) = TagElement(
    "script",
    attrs,
    null
)

@Composable
fun Base(
    attrs: AttrsScope<HTMLBaseElement>.() -> Unit = {}
) = TagElement(
    "base",
    attrs,
    null
)

@Composable
fun Link(
    attrs: AttrsScope<HTMLLinkElement>.() -> Unit = {}
) = TagElement(
    "link",
    attrs,
    null
)

fun AttrsScope<HTMLScriptElement>.src(value: String) =
    attr("src", value)

fun AttrsScope<HTMLScriptElement>.defer() =
    attr("defer", "")

fun AttrsScope<HTMLLinkElement>.href(value: String) =
    attr("href", value)

fun AttrsScope<HTMLLinkElement>.rel(value: String) =
    attr("rel", value)

fun AttrsScope<HTMLBaseElement>.href(value: String) =
    attr("href", value)