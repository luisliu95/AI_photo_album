# AI Moments Studio

基于 Stitch `HarmonyOS AI Moments Studio` 设计重构的 Jetpack Compose Android 交互原型。

## 已实现

- Gallery 瀑布流首页、真实 Stitch 本地素材与首次 AI 权限流程
- 素材筛选、多选编号、数量/时长/缩略图联动
- AI 系统设置、首次创作偏好、五阶段分析、后台运行与取消
- 视频时间线、版本管理、AI 对话修改、音乐卡点/混音、逐段字幕
- 结果预览、导出成功与 Android 系统分享
- Live 图筛选瀑布流、详情、逐帧封面与调节滑杆
- 任务三 Tab、生成进度、失败重试和分享
- 相册右侧 AI 高光小精灵、展开气泡与关闭状态
- 五步首次引导：价值、案例、隐私、创作方向、自动生成设置
- 引导完成、次日提醒和 AI 高光结果聚合页

AI、视频编码与相册写入使用本地 UI 状态模拟。

## 推荐 Demo 路径（约 5 分钟）

1. **相册首页** → 点击右侧 AI 小精灵 → 完成引导（选创作方向、提醒时间）
2. **AI 高光助手** → 「开始 AI 创作」→ 选择素材 → 创作设置（调整视频长度）
3. **AI 分析** → 等待完成 → **AI 视频剪辑**（预览、时间轴、版本切换）
4. **导出** → 分享作品 → **作品中心** 查看任务

## 运行

1. 使用 Android Studio 打开本目录。
2. 选择 API 35 模拟器或 Android 8.0 以上真机。
3. 运行 `app`。

命令行构建（Linux / macOS / Git Bash）：

```bash
./gradlew assembleDebug
```

云端打包：推送代码到 GitHub 后，在 **Actions → Build APK** 手动运行，从 Artifacts 下载 `app-debug.apk`。

**演示说明**：见 [DEMO.md](DEMO.md)（含 2 分钟 / 5 分钟演示路线与话术）。

## 分支说明

- `main`：主分支
- `m1`：当前 demo 优化分支（含 UI bug 修复与 GitHub Actions 打包）
