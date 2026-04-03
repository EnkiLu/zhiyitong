# 智医通 (ZhiyiTong) - AI 智能医疗助手

<p align="center">
  <img src="./front/src/assets/logo-zyt.svg" alt="智医通 Logo" width="120" height="120">
  <br>
  <strong>您的全天候 AI 医疗顾问</strong>
  <br>
  <span>基于 LangChain4j 与 Spring Boot 构建的智能医疗问答与挂号系统</span>
</p>

<p align="center">
  <a href="#项目简介">项目简介</a> •
  <a href="#功能特性">功能特性</a> •
  <a href="#技术栈">技术栈</a> •
  <a href="#快速开始">快速开始</a> •
  <a href="#项目结构">项目结构</a> •
  <a href="#配置说明">配置说明</a> •
  <a href="#使用示例">使用示例</a> •
  <a href="#贡献指南">贡献指南</a> •
  <a href="#许可证">许可证</a>
</p>

## 项目简介

**智医通**是一款基于人工智能的智能医疗助手系统，旨在为用户提供专业的医疗咨询、科室导诊、预约挂号等一站式服务。系统采用现代化的前后端分离架构，后端基于 Spring Boot 3.2 和 LangChain4j 框架，集成了多种大语言模型（OpenAI、Ollama、阿里云百炼），前端使用 Vue 3 + Element Plus 构建了美观易用的聊天界面。

### 核心价值
- **智能问诊**：基于临床实践的 AI 医疗顾问，提供详细准确的健康建议
- **AI 分导诊**：根据症状智能推荐最合适的就诊科室
- **一站式挂号**：实现号源查询、预约挂号、取消挂号的完整流程
- **知识增强**：通过 RAG（检索增强生成）技术，基于医院知识库提供精准回答

## 功能特性

### 🤖 AI 医疗顾问
- **症状咨询**：针对用户健康问题，提供病因分析、诊断流程、治疗方案和预防措施
- **用药指导**：指明适用的药品名称、剂量和疗程，提供用药注意事项
- **就医建议**：明确指示是否需要进一步检查或紧急就医

### 🏥 智能导诊与挂号
- **AI 分导诊**：根据病情和就医需求，智能推荐最合适的科室
- **号源查询**：实时查询医院各科室的可用号源
- **预约挂号**：智能预约挂号服务，支持选择科室、日期、时间和医生
- **取消挂号**：用户可方便地取消已预约的挂号

### 💬 智能对话系统
- **多轮对话**：基于 MongoDB 的对话记忆存储，支持连贯的上下文对话
- **流式响应**：实时流式输出 AI 回复，提升用户体验
- **个性化服务**：根据用户历史对话提供个性化的医疗建议

### 📚 知识增强（RAG）
- **医院知识库**：集成医院信息、科室介绍、就医流程等专业知识
- **向量检索**：使用 Pinecone 向量数据库实现高效的知识检索
- **PDF 解析**：支持解析医疗文档，丰富知识库内容

## 🎬 功能演示
 [演示视频](https://www.bilibili.com/video/BV1Xg93BXEwr/)
 <img width="1901" height="959" alt="image" src="https://github.com/user-attachments/assets/4d3a9753-3e04-4313-9a47-33ffe9c9ff0d" />



## 技术栈

### 后端技术
- **Java 17** + **Spring Boot 3.2.6** - 后端主框架
- **LangChain4j 1.0.0-beta3** - AI 应用框架
- **MyBatis-Plus 3.5.11** - 数据持久层
- **MySQL 8.0** - 关系型数据库（存储预约信息）
- **MongoDB** - NoSQL 数据库（存储对话记忆）
- **Pinecone** - 向量数据库（RAG 知识检索）
- **Knife4j 4.3.0** - API 文档生成

### AI 模型集成
- **Ollama** - 本地部署模型（支持 DeepSeek-R1:8B 等）
- **阿里云百炼** - 云端大模型（支持 Qwen-Max、Qwen-Plus）
- **OpenAI** - 预留接口，可扩展支持 GPT 系列

### 前端技术
- **Vue 3** - 前端框架
- **Element Plus** - UI 组件库
- **Vite 5** - 构建工具
- **Axios** - HTTP 客户端
- **CSS3** - 现代化响应式设计

### 开发与部署
- **Maven** - 项目构建与依赖管理
- **Git** - 版本控制
- **Docker** - 容器化支持（预留）

## 快速开始

### 环境要求
- JDK 17 或更高版本
- Node.js 18+ 和 npm/yarn
- MySQL 8.0+
- MongoDB 6.0+
- Ollama（可选，如需本地模型）
- 阿里云百炼 API Key（可选，如需云端模型）

### 1. 克隆项目
```bash
git clone https://github.com/EnkiLu/zhiyitong.git
cd zhiyitong
```

### 2. 数据库初始化
#### MySQL 数据库
```sql
CREATE database `Zhiyitong`;
Use `Zhiyitong`;
create table `appointment` (
	`id` bigint not null auto_increment,
    `username` varchar(50) not null,
    `id_card` varchar(18) not null,
   `department` varchar(50) not null,
    `date` varchar(10) not null,
    `time` varchar(10) not null,
    `doctor_name` varchar(50) default null,
    primary key (`id`)
);


CREATE TABLE doctor_schedule (
   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
   department  VARCHAR(64)  NOT NULL COMMENT '科室名称',
   doctor_name VARCHAR(32)  NOT NULL COMMENT '医生姓名',
   date        VARCHAR(16)  NOT NULL COMMENT '排班日期，格式 yyyy-MM-dd',
   time        VARCHAR(8)   NOT NULL COMMENT '排班时段：上午 / 下午',
   total       INT          NOT NULL DEFAULT 20 COMMENT '总号源数',
   booked      INT          NOT NULL DEFAULT 0  COMMENT '已预约数',
	INDEX idx_dept_date_time (department, date, time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='医生排班表';
```

#### MongoDB 数据库
确保 MongoDB 服务运行在 `localhost:27017`，系统会自动创建 `chat_memory_db` 数据库。

### 3. 后端配置与启动
#### 修改配置文件
编辑 `src/main/resources/application.properties`，根据实际情况调整配置：
```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/Zhiyitong?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false
spring.datasource.username=your_username
spring.datasource.password=your_password

# MongoDB 配置
spring.data.mongodb.uri=mongodb://localhost:27017/chat_memory_db

# AI 模型配置（选择一种或多种）
# Ollama 配置（本地模型）
langchain4j.ollama.chat-model.base-url=http://localhost:11434
langchain4j.ollama.chat-model.model-name=deepseek-r1:8b

# 阿里云百炼配置（云端模型）
langchain4j.community.dashscope.chat-model.api-key=your_api_key_here
langchain4j.community.dashscope.chat-model.model-name=qwen-max
```

#### 启动后端服务
```bash
# 进入项目根目录
cd zhiyitong

# 使用 Maven 编译并运行
mvn clean spring-boot:run
```
后端服务将在 `http://localhost:8080` 启动，API 文档可在 `http://localhost:8080/doc.html` 查看。

### 4. 前端配置与启动
#### 安装依赖
```bash
cd front
npm install  # 或使用 yarn
```

#### 启动前端开发服务器
```bash
npm run dev
```
前端服务将在 `http://localhost:5173` 启动。

### 5. 访问系统
打开浏览器访问 `http://localhost:5173`，即可开始使用智医通 AI 医疗助手。

## 项目结构

```
zhiyitong/
├── front/                          # 前端项目
│   ├── public/                     # 静态资源
│   ├── src/
│   │   ├── assets/                 # 图片、字体等资源
│   │   ├── components/             # Vue 组件
│   │   │   └── ChatWindow.vue      # 主聊天界面
│   │   ├── App.vue                 # 根组件
│   │   └── main.js                 # 入口文件
│   ├── package.json                # 前端依赖
│   └── vite.config.js              # Vite 配置
│
├── src/main/java/com/aimedical/java/ai/langchain4j/
│   ├── assistant/                  # AI 助手定义
│   │   └── ZhiyitongAgent.java     # 智医通 AI 服务接口
│   ├── config/                     # 配置类
│   │   ├── ZhiyitongAgentConfig.java    # AI 代理配置
│   │   └── EmbeddingStoreConfig.java    # 向量存储配置
│   ├── controller/                 # REST 控制器
│   │   └── ZhiyitongController.java     # 对话 API
│   ├── entity/                     # 实体类
│   │   └── Appointment.java        # 预约实体
│   ├── service/                    # 业务服务
│   │   ├── AppointmentService.java      # 预约服务接口
│   │   └── impl/                   # 服务实现
│   ├── mapper/                     # 数据访问层
│   │   └── AppointmentMapper.java  # 预约数据访问
│   ├── store/                      # 存储相关
│   │   └── MongoChatMemoryStore.java    # 聊天记忆存储
│   └── ZhiyiTongApp.java           # Spring Boot 启动类
│
├── src/main/resources/
│   ├── application.properties       # 应用配置
│   ├── Zhiyitong-prompt-template.txt # AI 系统提示词
│   └── mapper/                      # MyBatis XML 映射文件
│
├── hospital-rag/                    # RAG 知识库
│   ├── 医院信息.md                  # 医院基本信息
│   ├── 科室信息.md                  # 科室介绍
│   └── 神经内科.md                  # 专科知识
│
├── target/                          # 编译输出目录
├── pom.xml                          # Maven 依赖管理
└── README.md                        # 项目说明文档
```

## 配置说明

### AI 模型选择
系统支持多种 AI 模型，可根据需求配置：

1. **Ollama（推荐本地部署）**
   - 优点：数据本地化，隐私安全，无网络要求
   - 模型：DeepSeek-R1:8B、Llama 3 等
   - 配置：确保 Ollama 服务运行在 `http://localhost:11434`

2. **阿里云百炼（推荐云端）**
   - 优点：模型能力强，响应速度快
   - 模型：Qwen-Max、Qwen-Plus、Qwen-Turbo
   - 配置：需申请 API Key 并配置在 `application.properties`

3. **OpenAI（预留接口）**
   - 支持 GPT-4、GPT-3.5 等模型
   - 需配置 OpenAI API Key

### 向量数据库配置
系统使用 Pinecone 作为向量数据库，用于 RAG 知识检索：
```properties
# Pinecone 配置（需注册账号获取）
langchain4j.pinecone.api-key=your_pinecone_api_key
langchain4j.pinecone.environment=your_environment
langchain4j.pinecone.project-name=your_project_name
langchain4j.pinecone.index-name=medical_knowledge
```

### 系统提示词
系统的核心提示词位于 `src/main/resources/Zhiyitong-prompt-template.txt`，定义了 AI 助手的角色、能力和行为规范。可根据需要调整提示词内容。

## 使用示例

### 1. 症状咨询
```
用户：我最近咳嗽，喉咙痛，有点发烧，应该怎么办？
智医通：您好！根据您的描述，可能是上呼吸道感染。建议：
1. 多休息，多喝水，保持室内空气流通
2. 可服用布洛芬缓解发热和喉咙痛（成人剂量：每次200-400mg，每6-8小时一次）
3. 如症状持续3天未缓解，或出现呼吸困难、高热不退，请及时就医
4. 建议挂呼吸内科或全科医学科进一步检查
```

### 2. 科室导诊
```
用户：胃疼应该挂什么科？
智医通：胃疼通常需要就诊消化内科。如果伴有以下症状：
- 剧烈疼痛、呕血、黑便 → 急诊科
- 反酸、烧心、腹胀 → 消化内科
- 疼痛放射到背部 → 可能需要普外科会诊
建议您先挂消化内科进行胃镜检查。
```

### 3. 预约挂号
```
用户：我想预约消化内科的号
智医通：好的，请提供以下信息以便为您预约：
1. 姓名（必填）
2. 身份证号（必填）
3. 预约科室（必填）
4. 预约日期（格式：2025-04-14）
5. 预约时间（上午/下午）
6. 预约医生（可选）

请依次提供上述信息。
```

## 贡献指南

我们欢迎社区贡献！请按以下步骤参与：

### 报告问题
- 使用 GitHub Issues 报告 bug 或提出新功能建议
- 描述问题时请提供：环境信息、复现步骤、预期与实际行为

### 提交代码
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建 Pull Request

### 开发规范
- 遵循现有的代码风格和命名约定
- 确保所有测试通过
- 更新相关文档
- 为新增功能添加适当的测试用例

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 免责声明

**重要**：本系统提供的医疗建议仅供参考，不能替代专业医生的诊断和治疗。如有紧急医疗情况，请立即拨打急救电话或前往医院就诊。系统开发者和维护者对因使用本系统而产生的任何医疗问题不承担责任。

---

<p align="center">
  <strong>智医通 - 让医疗更智能，让健康更简单</strong>
  <br>
  <sub>© 2025 智医通项目组 • 用心打造每一行代码</sub>
</p>
