# Angular JS Sample

## Prerequisites

1. Install [Node](https://nodejs.org/en)
2. Add `GITHUB_TOKEN` environment variable. You may need to [create](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) GitHub token first.
3. Install dependencies in this folder

```shell
npm install
```

## Configuration

1. Configure sample by adding `sample-config.json` file in this folder:

```json
{
  "baseUrl": "<API base URL, e.g. https://api.simplyfi.com/>",
  "webUrl": "<URL to the embedded web app, e.g. https://go.simplyfi.com/>",
  "apiKey": "<Your organization API Key>"
}
```

2. Configure SimplyFi platform by adding `config.json` file in this folder. Take [this](https://go.simplyfi.com/config.json) file as base and replace necessary values:

```json
{
  "api_url": "<API base URL, e.g. https://api.simplyfi.com/>",
  "apps": {
    "go": "<URL to the main app, e.g. https://go.simplyfi.com/>",
    "signon": "<URL to the registration app, e.g. https://signon.simplyfi.com/>"
  },
  "remote_entries_url": "<CDN URL, e.g.https://static.simplyfi.com/>",
  "static_url": "<Static files URL, e.g. https://static.simplyfi.com/static/>"
}
```

## Start

To launch web app run:

```shell
npm run start
```