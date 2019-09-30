const formatDate = (ms) => {
  const taskDate = new Date(ms);
  return `${taskDate.getDate()}-${taskDate.getMonth() + 1}-${taskDate.getFullYear()}`;
};