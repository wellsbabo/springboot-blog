//삭제 기능
const deleteButton = document.getElementById('delete-btn');

if(deleteButton){
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        fetch(`/api/articles/${id}`, {
            method:'DELETE'
        })
            .then(() => {
                alert('삭제 완료');
                location.replace('/articles');
            });
    });
}