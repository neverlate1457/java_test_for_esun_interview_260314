### 玉山銀行面試實作
本專案為一個完整的前後端分離投票系統，具備後台管理項目與前台多選投票功能。系統設計遵循銀行業開發規範，強調資料一致性、安全性與模組化架構。

### 功能清單
1. 後台管理系統
- 項目展示：即時從資料庫獲取並列出所有投票項目。

- 項目新增：提供介面供管理員新增投票選項及描述。

- 項目刪除：實作物理刪除功能，並透過預存程序確保連帶投票紀錄之資料完整性。
2. 使用者投票介面
- 即時票數統計：頁面動態顯示各項目最新的累積票數。

- 多選投票功能：支援使用者單次勾選多個項目進行投票。
### 系統架構 
專案採用 Web + Application + Database 的三層式架構，確保系統的高擴展性與安全性：
- 前端：Vue 3, Vue Router, Axios, Vite
- 後端：Spring Boot 3, Spring Data JPA, Maven
    - 展示層：使用 Vue 3與 Vite 建構，負責使用者交互與資料呈現。
    - 業務層：使用 Spring Boot 3 與 Java 17，實現 RESTful API 與商業邏輯。
    - 資料層：使用 PostgreSQL，核心邏輯封裝於 Stored Procedures 中，並透過 JPA 參數化查詢確保安全性。
- 資料庫環境：Postgres 




### 實作細節
- 資料一致性：在進行「多選投票」時，後端採用 `@Transactional` 註解。若多筆投票中任一筆發生錯誤，系統將自動回滾 ，確保票數統計絕對精確。
- 安全性強化：
    - SQL Injection 防護：全數透過 Stored Procedure 存取，並於 JPA Repository 使用參數化查詢，杜絕非法指令注入。
    - XSS 防護：前端透過 Vue 3 內建的轉義機制處理輸入內容，防止惡意腳本執行。後端傳輸採 JSON 格式化，避免直接渲染未經過濾的 HTML 字串。
- 預存程序：遵循銀行開發規範，將 DML 邏輯封裝於資料庫層，降低網路傳輸負擔並提升安全性。
- 前後端路由管理：使用 Vue Router 實現前台投票與後台管理的路徑隔離，並配置 Vite Proxy 處理跨域問題。 
- 環境變數管理：敏感資訊（如資料庫帳密）抽離至`application-local.properties`與環境變數，不硬編碼於原始碼中。
### 目錄結構
```
voting-system/
├── backend/            # Spring Boot 後端專案 (Maven)
├── frontend/           # Vue.js 前端專案 (Vite)
├── DB/                 # 資料庫指令 (DDL & DML)
└── README.md           # 專案說明文件
```
### 啟動指南
1. 執行 `DB/ddl.sql` 建立資料表結構。
2. 執行 `DB/dml.sql` 建立所有的預存程序。
3. 後端啟動
需先於 `backend/src/main/resources/application-local.properties` 設定您的資料庫連線資訊。
``` Bash
cd backend
./mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=local
# API 預設運行於：http://localhost:8080
```

4. 前端啟動
``` Bash
npm install
npm run dev
# 前台投票：http://localhost:5173/
# 管理後台：http://localhost:5173/admin
```