# iOS Sample

To run first add a `sample/Secrets.xcconfig` file with following contents:

```text
API_KEY = { Your organization API Key }
API_URL = { API base url, e.g. https:/$()/api.simplyfi.com } 
WEB_URL = { Embedded app url, e.g. https:/$()/go.simplyfi.com } 
```

> [!NOTE]
> Make sure you escape double slash in URLs by putting `$()` between them.
> For example, `https:/$()/api.simplyfi.com`