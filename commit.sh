# ��������ļ��������⣻
echo "add..."
git add --all

# �����ύ��Ϣ��
echo Commit Message:
read message
echo "-----------------------"

# ����������ύ����������
echo "commit..."
git commit -m $message 

# �������ύ��Զ�ֿ̲⣻
echo "push..."
git push origin master

echo "-----------------------"
echo "��ϲ�������ύ�ɹ���"
read
