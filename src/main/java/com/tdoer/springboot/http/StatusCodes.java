/*
 * Copyright 2017-2019 T-Doer (tdoer.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tdoer.springboot.http;

import com.tdoer.springboot.annotation.ReasonPhrase;

/**
 * Known HTTP Status Codes, together with Extensible Error Codes by Bybon.
 *
 * @author Htinker Hu (htinker@163.com)
 * @create 2017-09-19
 */
public interface StatusCodes {

    /**
     * Known HTTP Status Codes
     */

    // 1xx Informational

    /**
     * {@code 100 Continue}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.2.1">HTTP/1.1: Semantics and Content;
     */

    @ReasonPhrase("请客户端继续请求")
    int CONTINUE = 100;
    /**
     * {@code 101 Switching Protocols}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.2.2">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器根据客户端的请求切换协议")
    int SWITCHING_PROTOCOLS = 101;
    /**
     * {@code 102 Processing}.
     * @see <a href="http://tools.ietf.org/html/rfc2518#section-10.1">WebDAV</a>
     */
    @ReasonPhrase("处理将被继续执行")
    int PROCESSING = 102;
    /**
     * {@code 103 Checkpoint}.
     * @see <a href="http://code.google.com/p/gears/wiki/ResumableHttpRequestsProposal">A proposal for supporting
     * resumable POST/PUT HTTP requests in HTTP/1.0</a>
     */
    @ReasonPhrase("提前预加载文件")
    int CHECKPOINT = 103;

    // 2xx Success

    /**
     * {@code 200 OK}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.1">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求成功")
    int OK = 200;
    /**
     * {@code 201 Created}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.2">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("成功请求并创建了新的资源")
    int CREATED = 201;
    /**
     * {@code 202 Accepted}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.3">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("已经接受请求，但未处理完成")
    int ACCEPTED = 202;
    /**
     * {@code 203 Non-Authoritative Information}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.4">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求成功，但返回的meta信息不在原始服务器")
    int NON_AUTHORITATIVE_INFORMATION = 203;
    /**
     * {@code 204 No Content}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.5">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器成功处理，但未返回内容")
    int NO_CONTENT = 204;
    /**
     * {@code 205 Reset Content}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.3.6">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器处理成功，用户终端应重置文档视图")
    int RESET_CONTENT = 205;
    /**
     * {@code 206 Partial Content}.
     * @see <a href="http://tools.ietf.org/html/rfc7233#section-4.1">HTTP/1.1: Range Requests;
     */
    @ReasonPhrase("服务器已经成功处理了部分 GET 请求")
    int PARTIAL_CONTENT = 206;
    /**
     * {@code 207 Multi-Status}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-13">WebDAV</a>
     */
    @ReasonPhrase("之后的消息体将是一个XML消息")
    int MULTI_STATUS = 207;
    /**
     * {@code 208 Already Reported}.
     * @see <a href="http://tools.ietf.org/html/rfc5842#section-7.1">WebDAV Binding Extensions</a>
     */
    @ReasonPhrase("绑定的构成已经在对当前请求的前一次响应中给予枚举，再次响应不再包含")
    int ALREADY_REPORTED = 208;
    /**
     * {@code 226 IM Used}.
     * @see <a href="http://tools.ietf.org/html/rfc3229#section-10.4.1">Delta encoding in HTTP</a>
     */
    @ReasonPhrase("服务器已满足了对资源的GET请求")
    int IM_USED = 226;

    // 3xx Redirection

    /**
     * {@code 300 Multiple Choices}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.1">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求的资源包括多个位置")
    int MULTIPLE_CHOICES = 300;
    /**
     * {@code 301 Moved Permanently}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.2">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求的资源已被永久移动到新URI")
    int MOVED_PERMANENTLY = 301;
    /**
     * {@code 302 Found}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.3">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求的资源已被临时移动到新URI")
    int FOUND = 302;
    /**
     * {@code 302 Moved Temporarily}.
     * @see <a href="http://tools.ietf.org/html/rfc1945#section-9.3">HTTP/1.0;
     * @deprecated in favor of {@link #FOUND} which will be returned from {@code HttpStatus.valueOf = 302)}
     */
    @Deprecated
    @ReasonPhrase("请求的资源已被临时移动到新URI")
    int MOVED_TEMPORARILY = 302;
    /**
     * {@code 303 See Other}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.4">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("查看其它地址")
    int SEE_OTHER = 303;
    /**
     * {@code 304 Not Modified}.
     * @see <a href="http://tools.ietf.org/html/rfc7232#section-4.1">HTTP/1.1: Conditional Requests;
     */
    @ReasonPhrase("所请求的资源未修改，服务器返不返回任何资源")
    int NOT_MODIFIED = 304;
    /**
     * {@code 305 Use Proxy}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.5">HTTP/1.1: Semantics and Content;
     * @deprecated due to security concerns regarding in-band configuration of a proxy
     */
    @Deprecated
    @ReasonPhrase("所请求的资源必须通过代理访问")
    int USE_PROXY = 305;
    /**
     * {@code 307 Temporary Redirect}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.4.7">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("临时重定向")
    int TEMPORARY_REDIRECT = 307;
    /**
     * {@code 308 Permanent Redirect}.
     * @see <a href="http://tools.ietf.org/html/rfc7238">RFC 7238</a>
     */
    @ReasonPhrase("所请求的资源已移动到Location标题给定的 URL")
    int PERMANENT_REDIRECT = 308;

    // --- 4xx Client Error ---

    /**
     * {@code 400 Bad Request}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.1">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("客户端请求的语法错误，服务器无法理解")
    int BAD_REQUEST = 400;
    /**
     * {@code 401 Unauthorized}.
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.1">HTTP/1.1: Authentication;
     */
    @ReasonPhrase("请求要求用户的身份认证")
    int UNAUTHORIZED = 401;
    /**
     * {@code 402 Payment Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.2">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("保留请求，将来使用")
    int PAYMENT_REQUIRED = 402;
    /**
     * {@code 403 Forbidden}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.3">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器理解请求客户端的请求，但是拒绝执行此请求")
    int FORBIDDEN = 403;
    /**
     * {@code 404 Not Found}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.4">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器无法根据客户端的请求找到资源")
    int NOT_FOUND = 404;
    /**
     * {@code 405 Method Not Allowed}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.5">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("客户端请求中的方法被禁止")
    int METHOD_NOT_ALLOWED = 405;
    /**
     * {@code 406 Not Acceptable}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.6">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器无法根据客户端请求的内容特性完成请求")
    int NOT_ACCEPTABLE = 406;
    /**
     * {@code 407 Proxy Authentication Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7235#section-3.2">HTTP/1.1: Authentication;
     */
    @ReasonPhrase("请求要求代理的身份认证")
    int PROXY_AUTHENTICATION_REQUIRED = 407;
    /**
     * {@code 408 Request Timeout}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.7">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器等待客户端发送的请求时间过长")
    int REQUEST_TIMEOUT = 408;
    /**
     * {@code 409 Conflict}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.8">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器处理请求时发生了冲突")
    int CONFLICT = 409;
    /**
     * {@code 410 Gone}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.9">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("客户端请求的资源已经不存在")
    int GONE = 410;
    /**
     * {@code 411 Length Required}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.10">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器无法处理客户端发送的不带Content-Length的请求信息")
    int LENGTH_REQUIRED = 411;
    /**
     * {@code 412 Precondition failed}.
     * @see <a href="http://tools.ietf.org/html/rfc7232#section-4.2">
     *     HTTP/1.1: Conditional Requests;
     */
    @ReasonPhrase("客户端请求信息的先决条件错误")
    int PRECONDITION_FAILED = 412;
    /**
     * {@code 413 Payload Too Large}.
     * @since 4.1
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.11">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("请求的实体过大，服务器无法处理，拒绝请求")
    int PAYLOAD_TOO_LARGE = 413;
    /**
     * {@code 413 Request Entity Too Large}.
     * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.4.14">HTTP/1.1;
     * @deprecated in favor of {@link #PAYLOAD_TOO_LARGE} which will be
     * returned from {@code HttpStatus.valueOf = 413)}
     */
    @Deprecated
    @ReasonPhrase("请求的实体过大，服务器无法处理，拒绝请求")
    int REQUEST_ENTITY_TOO_LARGE = 413;
    /**
     * {@code 414 URI Too Long}.
     * @since 4.1
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.12">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("URI过长，服务器无法处理")
    int URI_TOO_LONG = 414;
    /**
     * {@code 414 Request-URI Too Long}.
     * @see <a href="http://tools.ietf.org/html/rfc2616#section-10.4.15">HTTP/1.1;
     * @deprecated in favor of {@link #URI_TOO_LONG} which will be returned from {@code HttpStatus.valueOf = 414)}
     */
    @Deprecated
    @ReasonPhrase("请求的URI过长，服务器无法处理")
    int REQUEST_URI_TOO_LONG = 414;
    /**
     * {@code 415 Unsupported Media Type}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.13">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器无法处理请求附带的媒体格式")
    int UNSUPPORTED_MEDIA_TYPE = 415;
    /**
     * {@code 416 Requested Range Not Satisfiable}.
     * @see <a href="http://tools.ietf.org/html/rfc7233#section-4.4">HTTP/1.1: Range Requests;
     */
    @ReasonPhrase("客户端请求的范围无效")
    int REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    /**
     * {@code 417 Expectation Failed}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.5.14">
     *     HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器无法满足期望的请求头信息")
    int EXPECTATION_FAILED = 417;
    /**
     * {@code 418 I'm a teapot}.
     * @see <a href="http://tools.ietf.org/html/rfc2324#section-2.3.2">HTCPCP/1.0</a>
     */
    @ReasonPhrase("未知来源错误")
    int I_AM_A_TEAPOT = 418;
    /**
     * @deprecated See
     * <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    @ReasonPhrase("认证超时")
    int INSUFFICIENT_SPACE_ON_RESOURCE = 419;
    /**
     * @deprecated See
     * <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    @ReasonPhrase("方法失效")
    int METHOD_FAILURE = 420;
    /**
     * @deprecated
     * See <a href="http://tools.ietf.org/rfcdiff?difftype=--hwdiff&url2=draft-ietf-webdav-protocol-06.txt">
     *     WebDAV Draft Changes</a>
     */
    @Deprecated
    @ReasonPhrase("从当前客户端所在的IP地址到服务器的连接数超过了服务器许可的最大范围")
    int DESTINATION_LOCKED = 421;
    /**
     * {@code 422 Unprocessable Entity}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.2">WebDAV</a>
     */
    @ReasonPhrase("请求格式正确，但是由于含有语义错误，无法响应")
    int UNPROCESSABLE_ENTITY = 422;
    /**
     * {@code 423 Locked}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.3">WebDAV</a>
     */
    @ReasonPhrase("当前资源被锁定")
    int LOCKED = 423;
    /**
     * {@code 424 Failed Dependency}.
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.4">WebDAV</a>
     */
    @ReasonPhrase("由于之前的某个请求发生的错误，导致当前请求失败")
    int FAILED_DEPENDENCY = 424;
    /**
     * {@code 426 Upgrade Required}.
     * @see <a href="http://tools.ietf.org/html/rfc2817#section-6">Upgrading to TLS Within HTTP/1.1</a>
     */
    @ReasonPhrase("客户端应切换到TLS/1.0")
    int UPGRADE_REQUIRED = 426;
    /**
     * {@code 428 Precondition Required}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-3">Additional HTTP Status Codes</a>
     */
    @ReasonPhrase("原始服务器需要有条件的请求")
    int PRECONDITION_REQUIRED = 428;
    /**
     * {@code 429 Too Many Requests}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-4">Additional HTTP Status Codes</a>
     */
    @ReasonPhrase("请求过多")
    int TOO_MANY_REQUESTS = 429;
    /**
     * {@code 431 Request Header Fields Too Large}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-5">Additional HTTP Status Codes</a>
     */
    @ReasonPhrase("请求头部字段过大")
    int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;
    /**
     * {@code 451 Unavailable For Legal Reasons}.
     * @see <a href="https://tools.ietf.org/html/draft-ietf-httpbis-legally-restricted-status-04">
     * An HTTP Status Code to Report Legal Obstacles</a>
     * @since 4.3
     */
    @ReasonPhrase("该请求因法律原因不可用")
    int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    // --- 5xx Server Error ---

    /**
     * {@code 500 Internal Server Error}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.1">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器内部错误，无法完成请求")
    int INTERNAL_SERVER_ERROR = 500;
    /**
     * {@code 501 Not Implemented}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.2">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器不支持请求的功能，无法完成请求")
    int NOT_IMPLEMENTED = 501;
    /**
     * {@code 502 Bad Gateway}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.3">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("网关或代理服务器，从远端服务器接收到了一个无效的请求")
    int BAD_GATEWAY = 502;
    /**
     * {@code 503 Service Unavailable}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.4">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("由于超载或系统维护，服务器暂时的无法处理客户端的请求")
    int SERVICE_UNAVAILABLE = 503;
    /**
     * {@code 504 Gateway Timeout}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.5">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("网关或代理服务器，未及时从远端服务器获取请求")
    int GATEWAY_TIMEOUT = 504;
    /**
     * {@code 505 HTTP Version Not Supported}.
     * @see <a href="http://tools.ietf.org/html/rfc7231#section-6.6.6">HTTP/1.1: Semantics and Content;
     */
    @ReasonPhrase("服务器不支持请求的HTTP协议的版本，无法完成处理")
    int HTTP_VERSION_NOT_SUPPORTED = 505;
    /**
     * {@code 506 Variant Also Negotiates}
     * @see <a href="http://tools.ietf.org/html/rfc2295#section-8.1">Transparent Content Negotiation</a>
     */
    @ReasonPhrase("服务器存在内部配置错误")
    int VARIANT_ALSO_NEGOTIATES = 506;
    /**
     * {@code 507 Insufficient Storage}
     * @see <a href="http://tools.ietf.org/html/rfc4918#section-11.5">WebDAV</a>
     */
    @ReasonPhrase("服务器无法存储完成请求所必须的内容")
    int INSUFFICIENT_STORAGE = 507;
    /**
     * {@code 508 Loop Detected}
     * @see <a href="http://tools.ietf.org/html/rfc5842#section-7.2">WebDAV Binding Extensions</a>
     */
    @ReasonPhrase("发现环路")
    int LOOP_DETECTED = 508;
    /**
     * {@code 509 Bandwidth Limit Exceeded}
     */
    @ReasonPhrase("服务器达到带宽限制")
    int BANDWIDTH_LIMIT_EXCEEDED = 509;
    /**
     * {@code 510 Not Extended}
     * @see <a href="http://tools.ietf.org/html/rfc2774#section-7">HTTP Extension Framework</a>
     */
    @ReasonPhrase("获取资源所需要的策略并没有没满足")
    int NOT_EXTENDED = 510;
    /**
     * {@code 511 Network Authentication Required}.
     * @see <a href="http://tools.ietf.org/html/rfc6585#section-6">Additional HTTP Status Codes</a>
     */
    @ReasonPhrase("需要网络授权")
    int NETWORK_AUTHENTICATION_REQUIRED = 511;


    /*---------------------------------------------------------------------------
     * Bybon Extensible Error Codes:
     * 40001 ~ 40999 common product-side error codes
     * 50001 ~ 50999 common server-side error codes
     * --------------------------------------------------------------------------
     */

    // 40001 ~ 40999 common product-side error codes
    @ReasonPhrase("您的帐号密码错误，请重新输入。")
    int INVALID_LOGIN_PASSWORD = 40001;

    @ReasonPhrase("您的帐号已过期，请联系客服或管理人员。")
    int USER_ACCOUNT_EXPIRED = 40002;

    @ReasonPhrase("您的帐号被锁，请联系客服或管理人员。")
    int USER_ACCOUNT_LOCKED= 40003;

    @ReasonPhrase("您的帐号被禁用，请联系客服或管理人员。")
    int USER_ACCOUNT_DISABLED= 40004;

    @ReasonPhrase("您的登录凭证已过期，请联系客服或管理人员。")
    int USER_CREDENTIAL_EXPIRED = 40005;

    @ReasonPhrase("您的访问令牌已过期，请重新登录。")
    int ACCESS_TOKEN_EXPIRED = 40006;

    @ReasonPhrase("您的访问令牌已注销，请重新登录。")
    int ACCESS_TOKEN_REVOKED = 40007;

    @ReasonPhrase("您的访问令牌因新的登陆而无效了，请重新登录。")
    int ACCESS_TOKEN_REPLACED = 40008;

    @ReasonPhrase("您的权限不够，请联系客服或管理员。")
    int ACCESS_DENIED = 40009;

    @ReasonPhrase("您的帐号已在其它终端登陆，您将被迫登出。")
    int ACCESS_BLOCKED = 40010;

    @ReasonPhrase("无效请求，缺失应用ID。")
    int BAD_REQUEST_APP_ID_NOT_FOUND = 40011;

    @ReasonPhrase("无效请求，未知应用ID：{0}")
    int BAD_REQUEST_UNKNOWN_APP_ID = 40012;


    // 50001 ~ 50999 common server-side error codes
    @ReasonPhrase("Bad URI: {}")
    int INVALID_URI = 50001;


}
