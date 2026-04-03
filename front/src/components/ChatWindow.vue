<template>
  <div class="app-wrapper">
    <aside class="left-sidebar">
      <div class="sidebar-top">
        <div class="brand-header">
          <div class="logo-container">
            <div class="logo-glow-ring"></div>
            <img src="@/assets/logo-zyt.svg" alt="智医通" class="main-logo" />
          </div>
          <h1 class="app-name">智医通</h1>
          <div class="brand-tag">
            <span class="pulsing-dot"></span>
            AI 智能医疗助手
          </div>
        </div>

        <div class="action-section">
          <el-button class="new-chat-btn" @click="newChat">
            <i class="fa-solid fa-plus"></i>
            <span>开启新会话</span>
          </el-button>
        </div>

        <nav class="feature-guide">
          <div class="guide-item active">
            <div class="guide-icon"><i class="fa-solid fa-comments"></i></div>
            <span>智能问诊</span>
          </div>
          <div class="guide-item">
            <div class="guide-icon"><i class="fa-solid fa-book-medical"></i></div>
            <span>就医指南</span>
          </div>
          <div class="guide-item">
            <div class="guide-icon"><i class="fa-solid fa-calendar-check"></i></div>
            <span>预约挂号</span>
          </div>
          <div class="guide-item">
            <div class="guide-icon"><i class="fa-solid fa-file-medical"></i></div>
            <span>报告解读</span>
          </div>
        </nav>
      </div>

      <div class="sidebar-bottom">
        <div class="ai-status-card">
          <p class="status-title">系统状态</p>
          <div class="status-row">
            <span>AI 核心</span>
            <span class="status-online">● 在线</span>
          </div>
          <div class="status-row">
            <span>响应延迟</span>
            <span class="status-value">35ms</span>
          </div>
        </div>
      </div>
    </aside>

    <main class="main-stage">
      <header class="mobile-header">
        <div class="mobile-brand">
          <img src="@/assets/logo-zyt.svg" class="mini-logo" />
          <span>智医通</span>
        </div>
        <el-button size="small" circle @click="newChat" class="mobile-reset-btn">
          <i class="fa-solid fa-rotate-right"></i>
        </el-button>
      </header>

      <div class="chat-viewport">
        <div class="message-list" ref="messaggListRef">
          <div class="welcome-hero" v-if="messages.length < 2">
            <div class="hero-visual">
              <div class="ring ring-1"></div>
              <div class="ring ring-2"></div>
              <div class="ring ring-3"></div>
              <div class="hero-icon-center">
                <i class="fa-solid fa-heart-pulse"></i>
              </div>
            </div>
            <h2 class="hero-title">您好，我是 <span class="gradient-text">智医通</span></h2>
            <p class="hero-desc">您的全天候 AI 医疗顾问<br>为您提供症状初筛、科室导诊、报告解读等专业服务</p>
            <div class="quick-tags">
              <span @click="inputMessage = '感冒发烧挂什么科？'"><i class="fa-solid fa-thermometer"></i> 挂号建议</span>
              <span @click="inputMessage = '抽血需要空腹吗？'"><i class="fa-solid fa-droplet"></i> 采血须知</span>
              <span @click="inputMessage = '怎么查询电子报告？'"><i class="fa-solid fa-file-lines"></i> 报告查询</span>
              <span @click="inputMessage = '常见药物有哪些副作用？'"><i class="fa-solid fa-pills"></i> 用药咨询</span>
            </div>
          </div>

          <div
            v-for="(message, index) in messages"
            :key="index"
            :class="['message-row', message.isUser ? 'user-row' : 'bot-row']"
          >
            <div class="avatar-box">
              <div class="avatar-circle">
                <i :class="message.isUser ? 'fa-solid fa-user' : 'fa-solid fa-robot'"></i>
              </div>
            </div>
            <div class="message-container">
              <div class="message-bubble">
                <div class="content" v-html="message.content"></div>
                <div class="typing-indicator" v-if="message.isThinking || message.isTyping">
                  <span class="dot"></span><span class="dot"></span><span class="dot"></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <footer class="input-area">
        <div class="input-panel">
          <el-input
            v-model="inputMessage"
            placeholder="描述您的症状或健康疑问..."
            @keyup.enter="sendMessage"
            resize="none"
            type="textarea"
            :autosize="{ minRows: 1, maxRows: 4 }"
          ></el-input>
          <el-button type="primary" class="send-btn" :disabled="isSending" @click="sendMessage">
            <i class="fa-solid fa-paper-plane"></i>
          </el-button>
        </div>
        <div class="safe-disclaimer">
          <i class="fa-solid fa-shield-halved"></i>
          AI 生成内容仅供参考，不替代专业医生诊断，如有紧急情况请立即就医
        </div>
      </footer>
    </main>

    <aside class="right-sidebar">
      <div class="side-card tips-card">
        <h3 class="card-title"><i class="fa-solid fa-star"></i> 就医必读</h3>
        <ul class="tip-list">
          <li><strong>实名就医</strong><br>请携带患者本人有效身份证件</li>
          <li><strong>提前预约</strong><br>App 每日 16:00 更新号源</li>
          <li><strong>空腹检查</strong><br>血液检查前请禁食 8 小时</li>
        </ul>
      </div>

      <div class="side-card faq-card">
        <h3 class="card-title"><i class="fa-solid fa-circle-question"></i> 常见问题</h3>
        <div class="q-item" @click="inputMessage = '如何修改挂号信息？'">如何修改挂号信息？</div>
        <div class="q-item" @click="inputMessage = '缴费支持哪些方式？'">缴费支持哪些方式？</div>
        <div class="q-item" @click="inputMessage = '如何打印发票？'">如何打印发票？</div>
        <div class="q-item" @click="inputMessage = '检查报告多久出结果？'">检查报告多久出结果？</div>
      </div>

      <div class="side-card emergency-card">
        <h3 class="card-title"><i class="fa-solid fa-phone-volume"></i> 急诊热线</h3>
        <p class="phone-number">010-69156114</p>
        <p class="note">24 小时全天候服务</p>
      </div>
    </aside>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, nextTick } from 'vue'
import axios from 'axios'

const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])

onMounted(() => {
  initUUID()
  watch(messages, () => scrollToBottom(), { deep: true })
  hello()
})

const scrollToBottom = async () => {
  await nextTick()
  if (messaggListRef.value) {
    messaggListRef.value.scrollTo({ top: messaggListRef.value.scrollHeight, behavior: 'smooth' })
  }
}

const hello = () => { sendRequest('你好') }

const sendMessage = () => {
  if (inputMessage.value.trim() && !isSending.value) {
    sendRequest(inputMessage.value.trim())
    inputMessage.value = ''
  }
}

const sendRequest = (message) => {
  isSending.value = true
  if (messages.value.length > 0) {
    messages.value.push({ isUser: true, content: message, isTyping: false, isThinking: false })
  }
  const botMsg = { isUser: false, content: '', isTyping: true, isThinking: false }
  messages.value.push(botMsg)
  const lastMsg = messages.value[messages.value.length - 1]
  scrollToBottom()

  axios.post('/api/zhiyitong/chat', { memoryId: uuid.value, message }, {
    responseType: 'text',
    onDownloadProgress: (e) => {
      lastMsg.content = convertStreamOutput(e.event.target.responseText)
      scrollToBottom()
    },
  })
    .then(() => {
      lastMsg.isTyping = false
      isSending.value = false
    })
    .catch(() => {
      lastMsg.content = '网络异常，请尝试刷新页面。'
      lastMsg.isTyping = false
      isSending.value = false
    })
}

const initUUID = () => {
  let storedUUID = localStorage.getItem('user_uuid')
  if (!storedUUID) {
    storedUUID = Math.floor(Math.random() * 1000000)
    localStorage.setItem('user_uuid', storedUUID)
  }
  uuid.value = storedUUID
}

const convertStreamOutput = (output) => {
  if (!output) return ''
  return output
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/\n/g, '<br>')
}

const newChat = () => {
  localStorage.removeItem('user_uuid')
  window.location.reload()
}
</script>

<style scoped>
:root {
  --primary: #1a6fc4;
  --primary-light: #dbeafe;
  --primary-dark: #1e40af;
  --text-dark: #0f172a;
  --text-muted: #64748b;
  --bg-app: #f0f4f8;
}

.app-wrapper {
  display: flex;
  height: 100vh;
  width: 100vw;
  background: var(--bg-app);
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "PingFang SC", "Microsoft YaHei", sans-serif;
}

/* ===== LEFT SIDEBAR ===== */
.left-sidebar {
  width: 256px;
  background: linear-gradient(180deg, #0f172a 0%, #1e3a5f 100%);
  display: flex;
  flex-direction: column;
  padding: 28px 16px;
  flex-shrink: 0;
  justify-content: space-between;
  box-shadow: 4px 0 24px rgba(0,0,0,0.2);
  z-index: 10;
}

.brand-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 28px;
}

.logo-container {
  position: relative;
  width: 100px;
  height: 100px;
  margin-bottom: 16px;
}

.logo-glow-ring {
  position: absolute;
  inset: -6px;
  border-radius: 28px;
  background: conic-gradient(from 0deg, transparent 0%, rgba(14,165,233,0.6) 25%, transparent 50%, rgba(14,165,233,0.6) 75%, transparent 100%);
  animation: spin-glow 4s linear infinite;
  z-index: 0;
}

.main-logo {
  position: relative;
  z-index: 1;
  width: 100px;
  height: 100px;
  border-radius: 22px;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.logo-container:hover .main-logo { transform: scale(1.06); }

.app-name {
  font-size: 22px;
  font-weight: 800;
  color: #ffffff;
  letter-spacing: 2px;
  margin: 0 0 8px;
}

.brand-tag {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
  color: rgba(255,255,255,0.55);
}

.pulsing-dot {
  width: 7px;
  height: 7px;
  background: #34d399;
  border-radius: 50%;
  animation: pulse-dot 2s infinite;
}

.action-section { width: 100%; margin-bottom: 24px; }

.new-chat-btn {
  width: 100%;
  height: 44px;
  background: linear-gradient(135deg, #1a6fc4 0%, #0ea5e9 100%) !important;
  color: white !important;
  border: none !important;
  border-radius: 12px !important;
  font-weight: 600 !important;
  font-size: 14px !important;
  box-shadow: 0 4px 16px rgba(14,165,233,0.35) !important;
  transition: all 0.3s ease !important;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.new-chat-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 24px rgba(14,165,233,0.45) !important;
}

.feature-guide { width: 100%; }

.guide-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 11px 14px;
  border-radius: 10px;
  color: rgba(255,255,255,0.55);
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
  margin-bottom: 4px;
}

.guide-item:hover { background: rgba(255,255,255,0.08); color: rgba(255,255,255,0.9); }

.guide-item.active {
  background: rgba(14,165,233,0.2);
  color: #7dd3fc;
  font-weight: 600;
}

.guide-icon {
  width: 30px;
  height: 30px;
  border-radius: 8px;
  background: rgba(255,255,255,0.08);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
}

.guide-item.active .guide-icon { background: rgba(14,165,233,0.25); }

.ai-status-card {
  background: rgba(255,255,255,0.06);
  border: 1px solid rgba(255,255,255,0.1);
  border-radius: 12px;
  padding: 14px;
}

.status-title {
  font-size: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: rgba(255,255,255,0.35);
  margin: 0 0 10px;
}

.status-row {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  color: rgba(255,255,255,0.6);
  margin-bottom: 6px;
}

.status-online { color: #34d399; font-weight: 600; }
.status-value { color: #7dd3fc; font-weight: 600; }

/* ===== MAIN STAGE ===== */
.main-stage {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #ffffff;
  height: 100vh;
  overflow: hidden;
  position: relative;
}

.main-stage::before {
  content: '';
  position: absolute;
  top: 0; left: 0; right: 0;
  height: 3px;
  background: linear-gradient(90deg, #1a6fc4, #0ea5e9, #10b981);
  z-index: 5;
}

.mobile-header { display: none; }

.chat-viewport {
  flex: 1;
  display: flex;
  flex-direction: column;
  max-width: 860px;
  width: 100%;
  margin: 0 auto;
  overflow: hidden;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 32px 24px 16px;
  scroll-behavior: smooth;
}

.message-list::-webkit-scrollbar { width: 5px; }
.message-list::-webkit-scrollbar-thumb { background: #e2e8f0; border-radius: 10px; }
.message-list::-webkit-scrollbar-track { background: transparent; }

/* ===== WELCOME HERO ===== */
.welcome-hero {
  text-align: center;
  padding: 48px 20px 32px;
  animation: fade-up 0.6s ease;
}

.hero-visual {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto 28px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.ring {
  position: absolute;
  border-radius: 50%;
  border: 2px solid rgba(14,165,233,0.25);
}

.ring-1 { width: 120px; height: 120px; animation: ring-pulse 3s ease-in-out infinite; }
.ring-2 { width: 88px; height: 88px; animation: ring-pulse 3s ease-in-out infinite 0.5s; }
.ring-3 { width: 58px; height: 58px; animation: ring-pulse 3s ease-in-out infinite 1s; }

.hero-icon-center {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #1a6fc4, #0ea5e9);
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 26px;
  color: white;
  box-shadow: 0 8px 24px rgba(14,165,233,0.4);
  z-index: 1;
  animation: icon-float 4s ease-in-out infinite;
}

.hero-title {
  font-size: 26px;
  font-weight: 700;
  color: var(--text-dark);
  margin-bottom: 12px;
}

.gradient-text {
  background: linear-gradient(135deg, #1a6fc4, #0ea5e9);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.hero-desc {
  color: var(--text-muted);
  font-size: 15px;
  line-height: 1.7;
  margin-bottom: 28px;
}

.quick-tags {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.quick-tags span {
  padding: 9px 18px;
  background: #f8fafc;
  border: 1.5px solid #e2e8f0;
  border-radius: 24px;
  font-size: 13px;
  color: var(--text-muted);
  cursor: pointer;
  transition: all 0.25s ease;
  display: flex;
  align-items: center;
  gap: 6px;
}

.quick-tags span:hover {
  border-color: #0ea5e9;
  color: #0ea5e9;
  background: #f0f9ff;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(14,165,233,0.15);
}

/* ===== MESSAGES ===== */
.message-row {
  display: flex;
  margin-bottom: 24px;
  width: 100%;
  animation: slide-in 0.35s ease;
}

.user-row { flex-direction: row-reverse; }
.avatar-box { width: 42px; flex-shrink: 0; }

.avatar-circle {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.bot-row .avatar-circle {
  background: linear-gradient(135deg, #1a6fc4, #0ea5e9);
  color: white;
  box-shadow: 0 4px 12px rgba(14,165,233,0.3);
}

.user-row .avatar-circle {
  background: linear-gradient(135deg, #059669, #10b981);
  color: white;
  box-shadow: 0 4px 12px rgba(16,185,129,0.3);
}

.message-container { max-width: 72%; margin: 0 12px; }

.message-bubble {
  padding: 14px 18px;
  border-radius: 18px;
  font-size: 15px;
  line-height: 1.65;
  position: relative;
  word-break: break-word;
}

.bot-row .message-bubble {
  background: #f1f5f9;
  color: var(--text-dark);
  border-top-left-radius: 4px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.user-row .message-bubble {
  background: linear-gradient(135deg, #1a6fc4, #0ea5e9);
  color: #ffffff;
  border-top-right-radius: 4px;
  box-shadow: 0 4px 16px rgba(14,165,233,0.3);
}

.user-row .content { color: #ffffff !important; }

/* ===== INPUT AREA ===== */
.input-area {
  padding: 16px 24px 20px;
  background: #ffffff;
  border-top: 1px solid #f1f5f9;
  flex-shrink: 0;
  max-width: 860px;
  width: 100%;
  margin: 0 auto;
  align-self: stretch;
  box-sizing: border-box;
}

.input-panel {
  display: flex;
  align-items: flex-end;
  gap: 10px;
  background: #f8fafc;
  padding: 10px 14px;
  border-radius: 18px;
  border: 1.5px solid #e2e8f0;
  transition: all 0.25s ease;
}

.input-panel:focus-within {
  border-color: #0ea5e9;
  background: #ffffff;
  box-shadow: 0 0 0 4px rgba(14,165,233,0.08);
}

.input-panel :deep(.el-textarea__inner) {
  border: none;
  background: transparent;
  box-shadow: none;
  font-size: 15px;
  padding: 4px 0;
  color: var(--text-dark);
}

.send-btn {
  height: 40px !important;
  width: 40px !important;
  min-width: 40px !important;
  border-radius: 12px !important;
  padding: 0 !important;
  background: linear-gradient(135deg, #1a6fc4, #0ea5e9) !important;
  border: none !important;
  box-shadow: 0 4px 12px rgba(14,165,233,0.3) !important;
  transition: all 0.25s ease !important;
  flex-shrink: 0;
}

.send-btn:hover:not(:disabled) {
  transform: scale(1.08);
  box-shadow: 0 6px 18px rgba(14,165,233,0.4) !important;
}

.safe-disclaimer {
  text-align: center;
  font-size: 11.5px;
  color: #94a3b8;
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}

/* ===== RIGHT SIDEBAR ===== */
.right-sidebar {
  width: 252px;
  background: #fafbfc;
  border-left: 1px solid #edf2f7;
  display: flex;
  flex-direction: column;
  padding: 24px 16px;
  flex-shrink: 0;
  overflow-y: auto;
  gap: 16px;
}

.right-sidebar::-webkit-scrollbar { width: 0; }

.side-card {
  background: #ffffff;
  border-radius: 16px;
  padding: 18px;
  border: 1px solid #edf2f7;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  transition: all 0.3s ease;
}

.side-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.08);
}

.card-title {
  font-size: 14px;
  font-weight: 700;
  margin-bottom: 14px;
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--text-dark);
}

.tips-card { background: linear-gradient(135deg, #ffffff, #f0f9ff); }
.tips-card .card-title i { color: #f59e0b; }

.tip-list { list-style: none; padding: 0; margin: 0; }

.tip-list li {
  font-size: 12.5px;
  color: #475569;
  padding: 10px 10px 10px 14px;
  border-left: 3px solid #bfdbfe;
  margin-bottom: 10px;
  border-radius: 0 8px 8px 0;
  background: rgba(219,234,254,0.3);
  line-height: 1.6;
}

.tip-list li:last-child { margin-bottom: 0; }
.tip-list li strong { display: block; color: #1e40af; font-size: 12px; margin-bottom: 2px; }

.faq-card { background: linear-gradient(135deg, #ffffff, #f5f3ff); }
.faq-card .card-title i { color: #8b5cf6; }

.q-item {
  font-size: 12.5px;
  color: #475569;
  padding: 9px 10px;
  margin-bottom: 6px;
  background: rgba(245,243,255,0.5);
  border: 1px solid rgba(139,92,246,0.1);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
}

.q-item::before { content: "›"; color: #8b5cf6; margin-right: 8px; font-size: 15px; font-weight: 700; }
.q-item:hover { background: #f5f3ff; color: #7c3aed; border-color: rgba(139,92,246,0.3); padding-left: 14px; }
.q-item:last-child { margin-bottom: 0; }

.emergency-card {
  background: #fff1f2 !important;
  border: 1.5px solid #fca5a5 !important;
  text-align: center;
  animation: emergency-pulse 3s ease-in-out infinite;
}

.emergency-card .card-title { color: #dc2626 !important; justify-content: center; }
.emergency-card .card-title i { color: #dc2626; }

.phone-number {
  font-size: 20px;
  font-weight: 900;
  color: #dc2626;
  letter-spacing: 1px;
  margin: 8px 0 4px;
}

.note { font-size: 11px; color: #ef4444; opacity: 0.8; margin: 0; }

/* ===== TYPING ===== */
.typing-indicator { margin-top: 8px; display: flex; gap: 4px; }

.typing-indicator .dot {
  display: inline-block;
  width: 7px;
  height: 7px;
  background: #94a3b8;
  border-radius: 50%;
  animation: typing-bounce 1.2s infinite;
}

.typing-indicator .dot:nth-child(2) { animation-delay: 0.2s; }
.typing-indicator .dot:nth-child(3) { animation-delay: 0.4s; }

/* ===== MOBILE ===== */
.mobile-reset-btn { border: 1px solid rgba(255,255,255,0.2) !important; color: white !important; }

@media (max-width: 1100px) { .right-sidebar { display: none; } }

@media (max-width: 768px) {
  .left-sidebar { display: none; }
  .mobile-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 16px;
    background: #0f172a;
    border-bottom: 1px solid rgba(255,255,255,0.1);
  }
  .mobile-brand { display: flex; align-items: center; gap: 10px; font-weight: 700; font-size: 16px; color: #ffffff; }
  .mini-logo { width: 30px; height: 30px; border-radius: 8px; }
  .chat-viewport { width: 100%; }
  .message-list { padding: 16px; }
  .message-container { max-width: 85%; }
  .input-area { padding: 12px 16px 16px; max-width: 100%; }
}

/* ===== ANIMATIONS ===== */
@keyframes spin-glow {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes pulse-dot {
  0% { transform: scale(0.9); box-shadow: 0 0 0 0 rgba(52,211,153,0.7); }
  70% { transform: scale(1); box-shadow: 0 0 0 6px rgba(52,211,153,0); }
  100% { transform: scale(0.9); box-shadow: 0 0 0 0 rgba(52,211,153,0); }
}

@keyframes ring-pulse {
  0%, 100% { transform: scale(1); opacity: 0.3; }
  50% { transform: scale(1.06); opacity: 0.6; }
}

@keyframes icon-float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-6px); }
}

@keyframes slide-in {
  from { opacity: 0; transform: translateY(12px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fade-up {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes typing-bounce {
  0%, 100% { transform: translateY(0); opacity: 0.4; }
  50% { transform: translateY(-5px); opacity: 1; }
}

@keyframes emergency-pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgba(239,68,68,0.15); }
  50% { box-shadow: 0 0 16px 4px rgba(239,68,68,0.25); }
}
</style>
