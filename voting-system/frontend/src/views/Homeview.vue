<template>
  <div id="app">
    <header style="background-color: #009539; color: white; padding: 15px; border-radius: 8px 8px 0 0;">
      <h1 style="margin: 0;">玉山銀行投票系統</h1>
    </header>

    <main style="padding: 20px; border: 1px solid #ddd; border-top: none; border-radius: 0 0 8px 8px; background: white;">
      <div v-if="items.length > 0">
        <p style="text-align: left; color: #666;">請選擇您想投票的項目（可多選）：</p>
        
        <div v-for="item in items" :key="item.itemId" 
             style="display: flex; align-items: center; border-bottom: 1px solid #eee; padding: 15px 0;">
          <input type="checkbox" :id="'item-' + item.itemId" :value="item.itemId" v-model="selectedIds"
                 style="width: 20px; height: 20px; cursor: pointer;">
          <label :for="'item-' + item.itemId" style="flex: 1; margin-left: 15px; text-align: left; cursor: pointer;">
            <strong style="font-size: 1.1em;">{{ item.itemName }}</strong>
            <br/>
            <small style="color: #888;">{{ item.description }}</small>
          </label>
          <span style="background: #f0f0f0; padding: 5px 12px; border-radius: 20px; font-weight: bold; color: #009539;">
            {{ item.totalVotes }} 票
          </span>
        </div>

        <div style="margin-top: 25px;">
          <input type="text" v-model="voterInfo" placeholder="請輸入您的姓名" 
                 style="width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;">
          
          <button @click="submitVotes" :disabled="selectedIds.length === 0 || !voterInfo"
                  style="width: 100%; padding: 12px; background-color: #009539; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1.1em; font-weight: bold;">
            {{ selectedIds.length > 0 ? `確認送出 (${selectedIds.length} 項)` : '請選擇項目' }}
          </button>
        </div>
      </div>
      
      <div v-else style="padding: 40px;">
        <p>正在從伺服器連線中...</p>
      </div>
    </main>
    
    <footer style="margin-top: 20px; font-size: 0.8em; color: #999;">
      Powered by Spring Boot & Vue 3 @ Esun Interview
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const items = ref([])
const selectedIds = ref([])
const voterInfo = ref('')

const fetchItems = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/votes/items')
    items.value = res.data
  } catch (err) {
    alert('無法獲取資料，請檢查後端服務')
  }
}

const submitVotes = async () => {
  try {
    const payload = {
      itemIds: selectedIds.value,
      voterInfo: voterInfo.value
    }
    
    await axios.post('http://localhost:8080/api/votes/submit', payload)
    
    alert('投票成功！')
    selectedIds.value = [] 
    fetchItems()           
  } catch (err) {
    console.error(err)
    alert('投票失敗，請檢查網路連線')
  }
}

onMounted(fetchItems)
</script>

<style>
body { background-color: #f5f5f5; margin: 0; padding: 20px; }
#app { font-family: 'PingFang TC', 'Microsoft JhengHei', sans-serif; max-width: 500px; margin: 0 auto; }
button:disabled { background-color: #ccc !important; cursor: not-allowed !important; }
button:hover:not(:disabled) { background-color: #007d30 !important; }
</style>