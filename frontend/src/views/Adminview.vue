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
            <button @click="openEditModal(item)" style="background: #f39c12; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer; margin-right: 5px;">
              編輯
            </button>
            <button @click="deleteItem(item.itemId)" style="background: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">
              刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showEditModal" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center;">
      <div style="background: white; padding: 30px; border-radius: 8px; width: 400px;">
        <h3>編輯投票項目 (ID: {{ editingItem.itemId }})</h3>
        <input v-model="editingItem.itemName" placeholder="項目名稱" style="width: 100%; padding: 8px; margin-bottom: 10px;">
        <textarea v-model="editingItem.description" placeholder="項目描述" style="width: 100%; padding: 8px; height: 80px; margin-bottom: 10px;"></textarea>
        <div style="text-align: right;">
          <button @click="showEditModal = false" style="background: #ccc; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer; margin-right: 10px;">取消</button>
          <button @click="submitUpdate" style="background: #009539; color: white; border: none; padding: 8px 15px; border-radius: 4px; cursor: pointer;">儲存修改</button>
        </div>
      </div>
    </div>
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

// 確保變數名稱一致
const showEditModal = ref(false);
const editingItem = ref({ itemId: null, itemName: '', description: '' });

// 1. 將名稱改為 openEditModal
const openEditModal = (item) => {
  // 複製一份資料到編輯表單
  editingItem.value = { ...item };
  showEditModal.value = true;
};

// 2. 提交更新時也要用對變數名稱
const submitUpdate = async () => {
  try {
    // 注意這裡是用 editingItem.value.itemId
    const res = await axios.put(`http://localhost:8080/api/votes/items/${editingItem.value.itemId}`, editingItem.value);
    alert(res.data.message || "更新成功");
    fetchItems(); 
    showEditModal.value = false;
  } catch (err) {
    alert("更新失敗：" + (err.response?.data?.message || "未知錯誤"));
  }
};

// --- 生命週期 ---
onMounted(() => {
  fetchItems()
})
</script>