package com.simplyfi.sdk.view

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.dom.TagElement
import org.jetbrains.compose.web.internal.runtime.ComposeWebInternalApi
import org.w3c.dom.HTMLBaseElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLScriptElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget

@Composable
internal fun Root(
    tagName: String = "th-root",
) = TagElement<HTMLElement>(tagName, null, null)

@Composable
internal fun Script(
    attrs: AttrsScope<HTMLScriptElement>.() -> Unit = {}
) = TagElement(
    "script",
    attrs,
    null
)

@Composable
internal fun Base(
    attrs: AttrsScope<HTMLBaseElement>.() -> Unit = {}
) = TagElement(
    "base",
    attrs,
    null
)

@Composable
internal fun Link(
    attrs: AttrsScope<HTMLLinkElement>.() -> Unit = {}
) = TagElement(
    "link",
    attrs,
    null
)

@Composable
internal fun IFrame(
    attrs: AttrsScope<HTMLIFrameElement>.() -> Unit
) = TagElement(
    "iframe",
    attrs,
    null
)

internal fun AttrsScope<HTMLScriptElement>.src(value: String) =
    attr("src", value)

internal fun AttrsScope<HTMLScriptElement>.defer() =
    attr("defer", "")

internal fun AttrsScope<HTMLLinkElement>.href(value: String) =
    attr("href", value)

internal fun AttrsScope<HTMLLinkElement>.rel(value: String) =
    attr("rel", value)

internal fun AttrsScope<HTMLBaseElement>.href(value: String) =
    attr("href", value)

internal fun AttrsScope<HTMLIFrameElement>.src(value: String) =
    attr("src", value)
