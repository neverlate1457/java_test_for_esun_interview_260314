<template>
  <div class="admin-panel" style="margin-top: 50px; border-top: 2px dashed #009539; padding-top: 20px;">
    <h2 style="color: #009539;">後台管理系統</h2>
    <router-link to="/" style="text-decoration: none; color: #666;">← 回到前台投票</router-link>

    <div style="background: #f9f9f9; padding: 15px; border-radius: 8px; margin: 20px 0;">
      <h3>新增投票項目</h3>
      <input v-model="newItem.itemName" placeholder="項目名稱" style="width: 80%; padding: 8px; margin-bottom: 10px;">
      <br>
      <textarea v-model="newItem.description" placeholder="項目描述" style="width: 80%; padding: 8px; height: 60px;"></textarea>
      <br>
      <button @click="addItem" style="background: #009539; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer;">
        確認新增
      </button>
    </div>

    <table style="width: 100%; border-collapse: collapse;">
      <thead>
        <tr style="background: #eee;">
          <th style="padding: 10px; border: 1px solid #ddd;">ID</th>
          <th style="padding: 10px; border: 1px solid #ddd;">項目名稱</th>
          <th style="padding: 10px; border: 1px solid #ddd;">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.itemId">
          <td style="padding: 10px; border: 1px solid #ddd;">{{ item.itemId }}</td>
          <td style="padding: 10px; border: 1px solid #ddd;">{{ item.itemName }}</td>
          <td style="padding: 10px; border: 1px solid #ddd;">
            <button @click="deleteItem(item.itemId)" style="background: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">
              刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue' 
import axios from 'axios'

// --- 狀態定義 ---
const items = ref([])
const newItem = ref({ itemName: '', description: '' })

// --- 功能函數 ---

// 1. 抓取資料
const fetchItems = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/votes/items')
    console.log("後台抓到的資料：", res.data)
    items.value = res.data
  } catch (err) {
    console.error("抓取失敗:", err)
  }
}

// 2. 新增項目
const addItem = async () => {
  if (!newItem.value.itemName) return alert('請輸入名稱')
  try {
    await axios.post('http://localhost:8080/api/votes/items', newItem.value)
    alert('新增成功')
    newItem.value = { itemName: '', description: '' }
    fetchItems() 
  } catch (err) {
    alert('新增失敗')
  }
}

// 3. 刪除項目
const deleteItem = async (id) => {
  if (!confirm('確定要刪除此項目嗎？相關投票紀錄也會一併刪除。')) return
  try {
    await axios.delete(`http://localhost:8080/api/votes/items/${id}`)
    alert('刪除成功')
    fetchItems() 
  } catch (err) {
    alert('刪除失敗')
  }
}

// --- 生命週期 ---
onMounted(() => {
  fetchItems()
})
</script>