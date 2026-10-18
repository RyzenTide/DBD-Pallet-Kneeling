# Kneeling Pallets

A complete, single-page Dead by Daylight fan site about a fictional term for wasting pallets. Dark horror styling, original inline SVG artwork, responsive layouts, reduced-motion support, and a random confession button. No fonts, libraries, APIs, database, or paid services are required.

## Run with Java

Install a **JDK 17 or newer** (not a minimal JRE). From the repository root:

```sh
./scripts/start.sh
```

On Windows, or without a POSIX shell:

```sh
java --add-modules jdk.httpserver server/Server.java
```

Visit `http://localhost:8080` in your own browser. Stop with Ctrl+C. The source-file launcher compiles in memory, so there is no build tool or dependency installation. The server supports GET and HEAD, sets security headers, and prevents reading files outside `public/`.

By default the server binds to loopback. Set `PORT` to change the port; use `HOST=0.0.0.0` only when you intend to accept connections from other machines:

```sh
HOST=0.0.0.0 PORT=8080 ./scripts/start.sh
```

For a friend on the same Wi-Fi, allow the port in your firewall and send `http://YOUR-LAN-IP:8080`. This is a local-network address, not a public Internet link.

## Static alternative — recommended for sharing

Java serves the assets, but adds no meaningful application logic: the random confessions run entirely in the browser. Every feature also works as a static site. Open `public/index.html` directly, or serve it locally:

```sh
python3 -m http.server 8081 --directory public --bind 127.0.0.1
```

### Publish for free with GitHub Pages

The included `.github/workflows/pages.yml` publishes only `public/` when `main` is updated.

1. In this GitHub repository, open **Settings → Pages**.
2. Under **Build and deployment → Source**, choose **GitHub Actions**. GitHub Pages must be available for your repository/account; a public repository can use it for free.
3. In **Actions → Publish static website**, choose **Run workflow** on `main` (or push another commit).
4. Wait for the workflow to succeed. The deployment summary and Settings → Pages show the actual public URL, normally `https://ryzentide.github.io/troll-website-ai/`.
5. Open that URL and test the confession button before sending it to a friend.

Relative asset paths support both the GitHub Pages repository subdirectory and a custom domain. The Java process is not used by GitHub Pages. Do not assume the site is published until the deployment succeeds.

### Publish the Java version

Use a host or machine with JDK 17+, copy this repository, and run the server with `HOST=0.0.0.0` and the host's assigned `PORT`. Put it behind the host's HTTPS reverse proxy. Keep the process supervised and configure firewall rules. Hosting Java publicly is optional; static hosting is simpler for this project. Do not expose development machine ports directly to the Internet just to share this page.

## Project layout

- `public/index.html` — all page content and original pallet illustration
- `public/style.css` — responsive theme, animations, and reduced-motion support
- `public/script.js` — eight random confessions; no consecutive duplicate responses
- `public/favicon.svg` — local favicon
- `server/Server.java` — dependency-free Java HTTP server
- `scripts/start.sh` — starts Java from the correct working directory
- `.github/workflows/pages.yml` — optional static deployment

## Checks

With Java running, confirm the page and assets return HTTP 200, missing files return 404, and unsupported methods return 405. Test the confession button repeatedly, desktop and narrow phone layouts, keyboard focus, and reduced-motion preferences. No frontend build step is needed.

This is an unofficial fan project, not affiliated with Behaviour Interactive. “Kneeling Pallets” is fictional. No official game artwork or logos are used.
