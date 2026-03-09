# 🚀 Como Fazer Push para GitHub e Compilar Automaticamente

## 📋 Pré-requisitos

1. **Conta GitHub** (https://github.com)
2. **Git instalado** no seu PC
3. **Token GitHub** (Personal Access Token)

---

## 🔑 Passo 1: Criar Token GitHub

1. Acesse https://github.com/settings/tokens
2. Clique em **"Generate new token"**
3. Selecione **"repo"** (acesso completo a repositórios)
4. Clique em **"Generate token"**
5. **Copie o token** (você não conseguirá vê-lo novamente!)

---

## 📂 Passo 2: Criar Repositório no GitHub

1. Acesse https://github.com/new
2. Digite o nome: **jarvis-android**
3. Descrição: **App Jarvis Android com 480 upgrades**
4. Selecione **Public** (para GitHub Actions funcionar)
5. Clique em **"Create repository"**
6. **Copie a URL** do repositório (ex: `https://github.com/seu-usuario/jarvis-android.git`)

---

## 💻 Passo 3: Fazer Push do Projeto

### No Windows (PowerShell):
```powershell
cd C:\caminho\para\jarvis-android
git init
git add .
git commit -m "App Jarvis Android com 480 upgrades"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/jarvis-android.git
git push -u origin main
```

### No Mac/Linux:
```bash
cd /caminho/para/jarvis-android
git init
git add .
git commit -m "App Jarvis Android com 480 upgrades"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/jarvis-android.git
git push -u origin main
```

**Quando pedir usuário/senha:**
- **Usuário:** seu nome de usuário GitHub
- **Senha:** o token que você copiou no Passo 1

---

## ⚙️ Passo 4: GitHub Actions Compilará Automaticamente

Após fazer push:

1. Acesse seu repositório no GitHub
2. Clique na aba **"Actions"**
3. Você verá o workflow **"Build Jarvis APK"** rodando
4. Aguarde a compilação (5-10 minutos)
5. Quando terminar, clique no workflow
6. Clique em **"Artifacts"**
7. Baixe **"app-debug"** (contém o APK compilado!)

---

## 📱 Passo 5: Instalar no Android

1. Extraia o arquivo **"app-debug"** que você baixou
2. Você terá o arquivo **"app-debug.apk"**
3. Copie para seu Android via USB
4. Abra um gerenciador de arquivos no Android
5. Localize o arquivo **"app-debug.apk"**
6. Toque nele para instalar
7. Clique em **"Instalar"**
8. Pronto! 🎉

---

## 🔄 Passo 6: Atualizar o Código

Sempre que você quiser atualizar o código:

```bash
cd /caminho/para/jarvis-android
git add .
git commit -m "Descrição da mudança"
git push
```

O GitHub Actions compilará automaticamente novamente!

---

## 🏷️ Passo 7: Criar Release (Opcional)

Para criar uma versão oficial:

```bash
git tag -a v1.0.0 -m "Versão 1.0.0 do Jarvis"
git push origin v1.0.0
```

O GitHub Actions criará uma Release automaticamente com o APK pronto para download!

---

## 🆘 Solução de Problemas

### Erro: "fatal: not a git repository"
**Solução:** Certifique-se de estar na pasta `jarvis-android/` e execute `git init`

### Erro: "Authentication failed"
**Solução:** Verifique se o token está correto e se você tem acesso ao repositório

### Erro: "Branch 'main' not found"
**Solução:** Execute `git branch -M main` antes de fazer push

### GitHub Actions não compila
**Solução:** 
1. Verifique se o repositório é **Public**
2. Verifique se o arquivo `.github/workflows/build-apk.yml` existe
3. Tente fazer push novamente

---

## 📊 Status da Compilação

Você pode acompanhar o status em:
- **Actions tab:** https://github.com/SEU_USUARIO/jarvis-android/actions
- **Releases:** https://github.com/SEU_USUARIO/jarvis-android/releases

---

## 🎉 Pronto!

Seu App Jarvis Android está sendo compilado automaticamente no GitHub! 🚀

**Sempre que você fizer push, um novo APK será gerado automaticamente!** 💪
