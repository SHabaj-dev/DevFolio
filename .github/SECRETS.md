# GitHub Actions Secrets — Setup Guide

This document explains how to configure the 4 GitHub Secrets required by
the release pipeline. **No secret values are stored in this file.**

---

## Required Secrets

| Secret Name | Description |
|-------------|-------------|
| `ANDROID_KEYSTORE` | Base64-encoded `.jks` keystore file |
| `KEY_ALIAS` | The alias of the key inside the keystore |
| `KEY_PASSWORD` | The password protecting the key entry |
| `STORE_PASSWORD` | The password protecting the keystore file |

---

## Step 1 — Generate a Keystore (run once, keep it safe offline)

Open a terminal and run:

```bash
keytool -genkey -v \
  -keystore devfolio-release.jks \
  -alias devfolio \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -storepass YOUR_STORE_PASSWORD \
  -keypass YOUR_KEY_PASSWORD \
  -dname "CN=Shabaj Ansari, OU=Android Dev, O=DevFolio, L=Noida, ST=UP, C=IN"
```

> ⚠️ Store the `.jks` file in a **secure location offline** (password manager, encrypted drive).
> Never commit it to git — it is excluded by `.gitignore`.

---

## Step 2 — Base64-encode the keystore

### macOS / Linux
```bash
base64 -i devfolio-release.jks | pbcopy   # macOS — copied to clipboard
base64 -i devfolio-release.jks            # Linux — print to stdout
```

### Windows (PowerShell)
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("devfolio-release.jks")) | clip
```

---

## Step 3 — Add secrets to GitHub

1. Open your repository on GitHub
2. Go to **Settings → Secrets and variables → Actions**
3. Click **"New repository secret"** for each:

| Secret Name | Value |
|-------------|-------|
| `ANDROID_KEYSTORE` | The full base64 string from Step 2 |
| `KEY_ALIAS` | `devfolio` (or whatever alias you chose) |
| `KEY_PASSWORD` | Your key password |
| `STORE_PASSWORD` | Your store password |

---

## Step 4 — Create your first release

```bash
# Tag the current commit with the first version
git tag v1.0.0

# Push the tag to GitHub — this triggers the release workflow
git push origin v1.0.0
```

GitHub Actions will:
1. Parse `v1.0.0` → `versionName=1.0.0`, `versionCode=10000`
2. Decode your keystore from secrets
3. Build a signed, R8-minified APK
4. Create a GitHub Release at `https://github.com/SHabaj-dev/DevFolio/releases/tag/v1.0.0`
5. Upload `DevFolio-v1.0.0.apk` as a downloadable asset

---

## Subsequent Releases

```bash
# Bug fix
git tag v1.0.1 && git push origin v1.0.1

# New feature
git tag v1.1.0 && git push origin v1.1.0

# Major version
git tag v2.0.0 && git push origin v2.0.0
```

The stable download link always resolves to the latest release:
```
https://github.com/SHabaj-dev/DevFolio/releases/latest
```

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `bad decrypt` / keystore error | Re-encode the `.jks` — ensure no trailing newline |
| `APK not found` | Check the `assembleRelease` step for R8 errors |
| `Permission denied on upload` | Ensure the workflow has `permissions: contents: write` |
| Workflow not triggering | Tags must match `v[0-9]+.[0-9]+.[0-9]+` exactly |
