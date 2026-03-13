import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/votes', 
  headers: { 'Content-Type': 'application/json' }
});

export default {
  // 取得所有項目
  getItems() {
    return apiClient.get('');
  },
  // 後台新增項目
  addItem(item) {
    return apiClient.post('/admin/item', item);
  },
  // 使用者投票 (傳送 ID 列表)
  submitVotes(itemIds) {
    return apiClient.post('/submit', {
      itemIds: itemIds,
      voterInfo: "User_" + Math.random().toString(36).substring(7)
    });
  }
};