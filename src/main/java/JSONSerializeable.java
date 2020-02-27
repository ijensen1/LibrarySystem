interface JSONSerializeable<E> {
    public String serialize();
    public E deserialize();
}
